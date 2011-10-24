package br.com.lawbook.business.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.junit.Ignore;
import org.junit.Test;

import br.com.lawbook.business.ProfileService;
import br.com.lawbook.business.UserService;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 24OCT2011-05
 * 
 */

public class ProfileServiceTest {

	private static Logger LOG = Logger.getLogger("ProfileServiceTest");
	
	@Test
	public void create() {
		Profile profile = new Profile();
		profile.setAboutMe("Lawbook Administrator account");
		profile.setAvatar("http://bit.ly/q7IyX9");
		profile.setBirth(getDate("01/01/2011"));
		profile.setFirstName("Admin");
		profile.setLastName("");
		profile.setUserOwner(UserService.getInstance().getUserByUserName("admin"));
		
		saveProfile(profile);
		
	}
	
	@Test
	public void getProfileByUserName() {
		Profile p = ProfileService.getInstance().getProfileByUserName("admin");
		assertNotNull(p);
		LOG.info("Profile " + p.getFirstName() + " " + p.getLastName() + " fetched successfully");
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
			LOG.info("Profile " + p.getFirstName() + " " + p.getLastName() + " created successfully");
			assertNotNull(profile.getId());
			return p;
		} catch (IllegalArgumentException e) {
			LOG.log(Level.WARNING, e.getMessage());
		} catch (HibernateException e) {
			LOG.log(Level.SEVERE, "Error saving new profile: " + profile.getFirstName() + " " + profile.getLastName() + "\n" + e.getMessage());
			fail(e.getMessage());
		}
		return profile;
	}
	
	@Ignore
	@Test
	public void getProfileByIdTest() {
		ProfileService service = ProfileService.getInstance();
		Profile p = new Profile();
		Long id = 15L;
		p.setId(id);
		service.create(p);
		service.getProfileById(id);
		assertTrue(service.getProfileById(id).equals(p));
	}
	
}
