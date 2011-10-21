package br.com.lawbook.business.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.lawbook.DAO.AuthorityDAO;
import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.DAO.UserDAO;
import br.com.lawbook.business.AuthorityService;
import br.com.lawbook.business.UserService;
import br.com.lawbook.model.Authority;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 20OCT2011-01 
 * 
 */
public class UserServiceTest {

	private static Logger LOG = LoggerFactory.getLogger(UserServiceTest.class);
	
	@Test
	public void test() {
		User user = new User();
		Authority authority = new Authority();
		authority.setName("ROLE_USER");
		Authority authority2 = new Authority();
		authority2.setName("ROLE_ADMIN");
		user.setEmail("jr@hotmail.com");
		user.setEnable(true);
		user.setPassword("12345");
		user.setUserName("jrales");
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		AuthorityDAO authDAO = factory.getAuthorityDAO();
		UserDAO userDAO = factory.getUserDAO();
		
		
		saveAuth(authority);
		saveAuth(authority2);
		saveUser(user);
		
		List<Authority> auths1 = authDAO.getAll();
		
		for (Authority a : auths1) {
			assertNotNull(a.getId());
		}
		
		User u = userDAO.getByUserName("jrales");
		
		assertNotNull(u.getId());
		assertEquals(user.getEmail(), u.getEmail());
		
		List<Authority> auths = new ArrayList<Authority>();
		auths.add(authority);
		auths.add(authority2);
		u.setAuthority(auths);
		updateUser(u);
		
	}

	private void saveAuth(Authority authority) {
		try {
			AuthorityService authorityService = AuthorityService.getInstance();
			authorityService.save(authority);
			LOG.info("Authority " + authority.getName() + " create successfully");
		} catch (IllegalArgumentException e) {
			LOG.error(e.getMessage());
		}
	}
	
	private void saveUser(User user) {
		try {
			UserService userService = UserService.getInstance();
			userService.create(user);
			LOG.info("User " + user.getUserName() + " create successfully");
		} catch (IllegalArgumentException e) {
			LOG.error(e.getMessage());
		}
	}
	
	private void updateUser(User user) {
		try {
			UserService userService = UserService.getInstance();
			userService.update(user);
			LOG.info("User " + user.getUserName() + " updated successfully");
		} catch (IllegalArgumentException e) {
			LOG.error(e.getMessage());
		}
	}

}
