package br.com.lawbook.business.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;

import br.com.lawbook.business.AuthorityService;
import br.com.lawbook.business.UserService;
import br.com.lawbook.model.Authority;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OCT2011-07
 * 
 */
public class UserServiceTest {

	private final static Logger LOG = Logger.getLogger("UserServiceTest");
	
	@Before
	public void create() {
		
		User publicUser = new User();
		publicUser.setId(1L);
		publicUser.setEmail("noexist@lawbook.com.br");
		publicUser.setEnable(false);
		publicUser.setPassword("12345");
		publicUser.setUserName("public");
		
		User user = new User();
		user.setEmail("wrongAdminEmail@lawbook.com.br"); // it will be changed on test "update" below
		user.setEnable(true);
		user.setPassword("12345");
		user.setUserName("admin");
		List<Authority> auths = new ArrayList<Authority>();
		auths.add(AuthorityService.getInstance().getByName("ADMIN"));
		user.setAuthority(auths);
		
		saveUser(publicUser, "12345");
		saveUser(user, "12345");
	}
	
	private User saveUser(User user, String passConfirmation) {
		try {
			User u = UserService.getInstance().create(user, passConfirmation);
			assertNotNull(user.getId());
			LOG.info("User " + user.getUserName() + " create successfully");
			return u;
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
	public void update() {
		User u = UserService.getInstance().getUserById(2L);
		assertEquals("wrongAdminEmail@lawbook.com.br", u.getEmail());
		
		u.setEmail("admin@lawbook.com.br");
		UserService.getInstance().update(u);
		
		User u2 = UserService.getInstance().getUserById(2L);
		assertEquals("admin@lawbook.com.br", u2.getEmail());
		LOG.info("User " + u2.getUserName() + " updated successfully");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void passConfirmationTest() {
		User user = new User();
		user.setPassword("123");
		UserService.getInstance().create(user, "321");
	}

}
