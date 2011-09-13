package br.com.lawbook.business.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;

import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Comment;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.DBMock;

/**
 * @author Edilson Luiz Ales Junior
 * @version 12SEP2011-02
 */

public class ProfileServiceTest {

	@Test
	public void getProfileByIdTest() {
		ProfileService service = ProfileService.getInstance();
		Profile p = new Profile();
		Long id = 15L;
		p.setId(id);
		service.save(p);
		service.getProfileById(id);
		assertTrue(service.getProfileById(id).equals(p));
	}
	
	
	@Test
	public void getStreamTest() {
		
		DBMock db = DBMock.getInstance();
		List<Profile> profiles = db.getTableProfile();
		assertNotNull(profiles);
		List<Post> posts = db.getTablePost();
		assertNotNull(posts);
		Profile me = profiles.get(4);
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println(me.getFirstName() + "'s Stream\n");
		for (Post post : posts) {
			for (Profile friend : me.getFriendsList()) {
				if (post.getSender().equals(friend)) {
					System.out.println(friend.getFirstName() + " wrote: " + post.getContent() + " at " + df.format(post.getDateTime().getTime()));
					if (post.getComments() != null && !post.getComments().isEmpty()) {
						System.out.println("Comments");
						for (Comment c : post.getComments()) {
							System.out.println(c.getSender().getFirstName() + " comment: " + c.getContent() + " at " + df.format(c.getDateTime().getTime()));
						}
					}
				}
			}
		}
	}

}
