package br.com.lawbook.business.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import br.com.lawbook.business.PostService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 15SEP2011-01
 * 
 */

public class PostServiceTest {
	
	@Test
	public void getStreamTest() {
		PostService ps = PostService.getInstance();
		ProfileService pfs = ProfileService.getInstance(); 
		Profile profile1 = new Profile();
		assertNull(ps.getStream(profile1.getFriendsList()));

		Profile profile2 = new Profile();
		profile1.setFirstName("Jr");
		profile2.setFirstName("Ales");
		ArrayList<Profile> friendList = new ArrayList<Profile>();
		friendList.add(profile2);
		profile1.setFriendsList(friendList);
		pfs.save(profile1);
		pfs.save(profile2);
		assertNotNull(ps.getStream(profile1.getFriendsList()));
	}
	
}
