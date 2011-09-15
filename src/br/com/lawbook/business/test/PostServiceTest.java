package br.com.lawbook.business.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import br.com.lawbook.business.PostService;
import br.com.lawbook.model.Profile;

public class PostServiceTest {
	
	@Test
	public void getStreamTest() {
		PostService service = PostService.getInstance();
		Profile profile1 = new Profile();
		assertNull(service.getStream(profile1.getFriendsList()));
		
		Profile profile2 = new Profile();
		ArrayList<Profile> friendList = new ArrayList<Profile>();
		friendList.add(profile2);
		profile1.setFriendsList(friendList);
		assertNotNull(service.getStream(profile1.getFriendsList()));
	}
	
}
