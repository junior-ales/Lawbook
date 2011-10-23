package br.com.lawbook.business.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.junit.Test;

import br.com.lawbook.business.AuthorityService;
import br.com.lawbook.business.UserService;
import br.com.lawbook.model.Authority;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 23OCT2011-03 
 * 
 */
public class UserServiceTest {

	private static Logger LOG = Logger.getLogger("UserServiceTest");
	
	@Test
	public void create() {
		User user = new User();
		
		user.setEmail("jrales");
		user.setEnable(true);
		user.setPassword("12345");
		user.setUserName("jrales@lawbook.com.br");
		
		List<Authority> auths = new ArrayList<Authority>();
		auths.add(AuthorityService.getInstance().getByName("USER"));
		user.setAuthority(auths);
		
		saveUser(user);
	}

	private User saveUser(User user) {
		try {
			User u = UserService.getInstance().create(user);
			LOG.info("User " + user.getUserName() + " create successfully");
			assertNotNull(user.getId());
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
