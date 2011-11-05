package br.com.lawbook.business.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.lawbook.business.AuthorityService;
import br.com.lawbook.business.UserService;
import br.com.lawbook.model.Authority;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-08
 * 
 */
public class UserServiceTest {

	private final static Logger LOG = Logger.getLogger("UserServiceTest");
	
	@BeforeClass
	public static void create() {
		
		User publicUser = new User();
		publicUser.setId(1L);
		publicUser.setEmail("temporary");
		publicUser.setEnable(false);
		publicUser.setPassword("12345");
		publicUser.setUserName("public");
		
		User user = new User();
		user.setEmail("admin@lawbook.com.br");
		user.setEnable(true);
		user.setPassword("1a2d3m4i5n");
		user.setUserName("admin");
		List<Authority> auths = new ArrayList<Authority>();
		auths.add(AuthorityService.getInstance().getByName("ADMIN"));
		user.setAuthority(auths);
		
		saveUser(publicUser, "12345");
		saveUser(user, "1a2d3m4i5n");
	}
	
	private static User saveUser(User user, String passConfirmation) {
		try {
			UserService.getInstance().create(user, passConfirmation);
			assertNotNull(user.getId());
			LOG.info("User " + user.getUserName() + " create successfully");
		} catch (IllegalArgumentException e) {
			LOG.log(Level.WARNING, e.getMessage());
		} catch (HibernateException e) {
			LOG.log(Level.SEVERE, "Error saving new user: " + user.getUserName() + "\n" + e.getMessage());
			fail(e.getMessage());
		}
		return user;
	}
	
	@Test
	public void getUserById() {
		Long id = 1L;
		User u = UserService.getInstance().getUserById(id);
		assertEquals(u.getId(), id);
	}
	
	@Test
	public void getUserByUserName() {
		String username = "admin";
		User user = UserService.getInstance().getUserByUserName(username);
		assertEquals(user.getUserName(), username);
	}
	
	@Test
	public void update() {
		String newEmail = Calendar.getInstance().getTimeInMillis() + "@lawbook.com.br";
		
		User user = new User();
		user = UserService.getInstance().getUserByUserName("public");
		assertFalse(user.getEmail().equals(newEmail));

		user.setEmail(newEmail);
		UserService.getInstance().update(user);
		
		user = null;
		user = UserService.getInstance().getUserByUserName("public");
		assertTrue(user.getEmail().equals(newEmail)); 

		LOG.info("User " + user.getUserName() + " updated successfully");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void passConfirmationTest() {
		User user = new User();
		user.setPassword("123");
		UserService.getInstance().create(user, "321");
	}

}
