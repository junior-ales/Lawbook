package br.com.lawbook.business.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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
 * @version 26OCT2011-06
 * 
 */
public class UserServiceTest {

	private static Logger LOG = Logger.getLogger("UserServiceTest");
	
	@Before
	public void create() {
		
		User publicUser = new User();
		publicUser.setId(0L);
		publicUser.setEmail("");
		publicUser.setEnable(false);
		publicUser.setPassword("");
		publicUser.setUserName("public");
		
		User user = new User();
		user.setEmail("admin@lawbook.com.br");
		user.setEnable(true);
		user.setPassword("12345");
		user.setUserName("admin");
		List<Authority> auths = new ArrayList<Authority>();
		auths.add(AuthorityService.getInstance().getByName("ADMIN"));
		user.setAuthority(auths);
		
		saveUser(publicUser, "");
		saveUser(user, "12345");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void passConfirmationTest() {
		User user = new User();
		user.setPassword("123");
		UserService.getInstance().create(user, "321");
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

}
