package br.com.lawbook.business.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Ignore;
import org.junit.Test;

import br.com.lawbook.business.PostService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 18OUT2011-03
 * 
 */
public class PostServiceTest {
	
	@Ignore
	@Test
	public void getStreamTest() {
		ProfileService pfs = ProfileService.getInstance();
		PostService ps = PostService.getInstance(); 
		Profile profile = pfs.getProfileByUserName("jrales");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("profile", profile);
		assertNotNull(ps.getStream(attributes));
	}
	
}
