package br.com.lawbook.business.test;

import org.junit.Test;

import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

@SuppressWarnings("unused")
public class ProfileServiceTest {
	
	
//	private User userSender;
//	private List<User> userReceivers;
//	private Profile profileSender;
//	private User ownUser;
//	private Profile ownProfile;
//	private Post ownPost;
//	private Post senderPost;
//	private Post receiverPost;
	
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
