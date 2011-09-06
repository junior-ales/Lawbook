package br.com.lawbook.business.test;

import org.junit.Test;

import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.DBMock;

@SuppressWarnings("unused")
public class ProfileServiceTest {
	
	@Test
	public void getFirstWallTest() {

		Profile profile = new Profile();
		
	}
	
	@Test
	public void getWallTest() {

		Profile profile = new Profile();
		Post post1 = new Post();
		Post post2 = new Post();
		
		profile.getWall().add(post1);
		profile.getWall().add(post2);
	}
	
}
