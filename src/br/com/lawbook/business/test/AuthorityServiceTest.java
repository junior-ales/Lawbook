package br.com.lawbook.business.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.lawbook.business.AuthorityService;
import br.com.lawbook.model.Authority;

/**
 * @author Edilson Luiz Ales Junior
 * @version 30OCT2011-04
 * 
 */
public class AuthorityServiceTest {
	
	private final static Logger LOG = Logger.getLogger("AuthorityServiceTest");

	@BeforeClass
	public static void create() {
		
		List<Authority> auths = new ArrayList<Authority>();
		auths.add(new Authority("ADMIN"));
		auths.add(new Authority("USER"));
		auths.add(new Authority("MANAGER"));
		
		for (Authority auth : auths) {
			saveAuth(auth);
		}
	}
	@Test
	public void getByName() {
		String authorityName = "ADMIN";
		Authority auth = new Authority(); 
		
		auth.setName("WRONG_NAME");
		assertFalse(auth.getName().equals(authorityName));
		
		auth = AuthorityService.getInstance().getByName(authorityName);
		assertTrue(auth.getName().equals(authorityName));
	}
	
	private static void saveAuth(Authority auth) {
		try {
			AuthorityService.getInstance().create(auth);
			assertNotNull(auth.getId());
			LOG.info("Authority " + auth.getName() + " create successfully");
		} catch (IllegalArgumentException e) {
			LOG.log(Level.WARNING, e.getMessage());
		} catch (HibernateException e) {
			LOG.log(Level.SEVERE, "Error saving new authority: " + auth);
			fail(e.getMessage());
		}
	}
}
