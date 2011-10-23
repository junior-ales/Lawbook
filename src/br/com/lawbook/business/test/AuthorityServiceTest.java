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
import br.com.lawbook.model.Authority;

/**
 * @author Edilson Luiz Ales Junior
 * @version 23OCT2011-01
 * 
 */
public class AuthorityServiceTest {
	
	private static Logger LOG = Logger.getLogger("AuthorityServiceTest");

	@Test
	public void create() {
		
		List<String> auths = new ArrayList<String>();
		auths.add("ADMIN");
		auths.add("USER");
		
		Authority authority;
		
		for (String auth : auths) {
			authority = saveAuth(auth);
			assertNotNull(authority.getId());
			LOG.info("Authority " + authority.getName() + " create successfully");
		}
	}
	
	private Authority saveAuth(String authorityName) {
		try {
			return AuthorityService.getInstance().create(authorityName);
		} catch (IllegalArgumentException e) {
			LOG.log(Level.WARNING, e.getMessage());
		} catch (HibernateException e) {
			LOG.log(Level.SEVERE, "Error saving new authority: " + authorityName);
			fail(e.getMessage());
		}
		return null;
	}
}
