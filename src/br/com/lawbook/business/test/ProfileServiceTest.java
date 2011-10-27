package br.com.lawbook.business.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;

import br.com.lawbook.business.ProfileService;
import br.com.lawbook.business.UserService;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 26OCT2011-07
 * 
 */
public class ProfileServiceTest {

	private static Logger LOG = Logger.getLogger("ProfileServiceTest");
	
	@Before
	public void create() {
		
		UserService userService = UserService.getInstance();
		
		Profile publicProfile = new Profile();
		publicProfile.setId(0L);
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
	
	private Calendar getDate(String dateString) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			c.setTime(df.parse(dateString));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	private Profile saveProfile(Profile profile) {
		try {
			Profile p = ProfileService.getInstance().create(profile);
			assertNotNull(profile.getId());
			LOG.info("Profile " + p.getFirstName() + " " + p.getLastName() + " created successfully");
			return p;
		} catch (IllegalArgumentException e) {
			LOG.log(Level.WARNING, e.getMessage());
		} catch (HibernateException e) {
			LOG.log(Level.SEVERE, "Error saving new profile: " + profile.getFirstName() + " " + profile.getLastName() + "\n" + e.getMessage());
			fail(e.getMessage());
		}
		return profile;
	}
	
}
