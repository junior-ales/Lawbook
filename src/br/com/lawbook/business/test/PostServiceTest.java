package br.com.lawbook.business.test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.lawbook.business.PostService;
import br.com.lawbook.model.Profile;

public class PostServiceTest {
	
	@Test
	public void getStreamTest() {
		PostService service = PostService.getInstance();
		assertNotNull(service.getStream(new Profile().getFriendsList()));
	}
	
}
