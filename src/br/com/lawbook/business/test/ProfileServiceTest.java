package br.com.lawbook.business.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.lawbook.business.ProfileService;
import br.com.lawbook.business.UserService;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-08
 * 
 */
public class ProfileServiceTest {

	private final static Logger LOG = Logger.getLogger("ProfileServiceTest");
	
	@BeforeClass
	public static void create() {
		
		UserService userService = UserService.getInstance();
		
		Profile publicProfile = new Profile();
		publicProfile.setFirstName("Public Profile");
		publicProfile.setLastName("");
		publicProfile.setUserOwner(userService.getUserByUserName("public"));
		
		Profile profile = new Profile();
		profile.setAboutMe("Lawbook Administrator account");
		profile.setAvatar("http://bit.ly/t456JJ");
		profile.setBirth(getDate("01/01/2011"));
		profile.setFirstName("Admin");
		profile.setLastName("");
		profile.setUserOwner(userService.getUserByUserName("admin"));
		
		saveProfile(publicProfile);
		saveProfile(profile);
		
	}
	
	@Test
	public void getProfileByUserName() {
		Profile p = ProfileService.getInstance().getProfileByUserName("admin");
		assertNotNull(p);
		LOG.info("Profile " + p.getFirstName() + " " + p.getLastName() + " fetched by user name successfully");
	}
	
	@Test
	public void getProfileByIdTest() {
		Profile p = ProfileService.getInstance().getProfileByUserId(1L);
		assertNotNull(p);
		LOG.info("Profile " + p.getFirstName() + " " + p.getLastName() + " fetched by user id successfully");
	}
	
	@Test
	public void friendship() {
		Profile p1 = ProfileService.getInstance().getProfileByUserName("admin");
		Profile p2 = ProfileService.getInstance().getPublicProfile();
		
		List<Profile> friends = new ArrayList<Profile>();
		friends.add(p2);
		p1.setFriends(friends);
		
		ProfileService.getInstance().update(p1);
		assertFalse(p1.getFriends().isEmpty());
		// TODO assertFalse(p2.getFriends().isEmpty());
	}
	
	private static Calendar getDate(String dateString) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			c.setTime(df.parse(dateString));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	private static void saveProfile(Profile profile) {
		try {
			ProfileService.getInstance().create(profile);
			assertNotNull(profile.getId());
			LOG.info("Profile " + profile.getFirstName() + " " + profile.getLastName() + " created successfully");
		} catch (IllegalArgumentException e) {
			LOG.log(Level.WARNING, e.getMessage());
		} catch (HibernateException e) {
			LOG.log(Level.SEVERE, "Error saving new profile: " + profile.getFirstName() + " " + profile.getLastName() + "\n" + e.getMessage());
			fail(e.getMessage());
		}
	}
	
}
