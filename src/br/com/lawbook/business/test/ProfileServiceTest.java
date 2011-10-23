package br.com.lawbook.business.test;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 01OCT2011-04
 * 
 */

public class ProfileServiceTest {

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
