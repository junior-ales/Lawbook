package br.com.lawbook.business.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.lawbook.business.PostService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 18NOV2011-07
 * 
 */
public class PostServiceTest {
	
	private final static Logger LOG = Logger.getLogger("PostServiceTest");
	
	@BeforeClass
	public static void post() {
		ProfileService profileService = ProfileService.getInstance();
		
		for (int i = 0; i < 20; i++) {
			Post post = new Post();
			post.setContent(Calendar.getInstance().getTimeInMillis() + " - Post content ");
			post.setSender(profileService.getProfileByUserName("admin"));
			post.setReceiver(profileService.getPublicProfile());
			savePost(post);
		}
	}
	
	@Test
	public void getStream() {
		Profile owner = ProfileService.getInstance().getProfileByUserName("admin");
		List<Post> stream = PostService.getInstance().getStream(owner, 1, 8);
		assertNotNull(stream);
		assertTrue(stream.size() == 8);
		assertFalse(stream.get(0).getContent().trim().equals(""));
	}
	
	@Test
	public void getWall() {
		Profile owner = ProfileService.getInstance().getProfileByUserName("admin");
		List<Post> wall = PostService.getInstance().getWall(owner, owner, 1, 8);
		assertNotNull(wall);
		assertTrue(wall.size() == 8);
		assertFalse(wall.get(0).getContent().trim().equals(""));
	}
	
	@Test
	public void getPostCount() {
		assertFalse(PostService.getInstance().getPostsCount() <= 0);
	}
	
	@Test
	public void deleteAndGetById() {
		ProfileService profileService = ProfileService.getInstance();

		Post post = new Post();
		post.setContent("Post that is gonna be deleted");
		post.setSender(profileService.getProfileByUserName("admin"));
		post.setReceiver(profileService.getPublicProfile());

		savePost(post);
		Long id = post.getId();
		
		PostService.getInstance().delete(post);
		
		assertNull(PostService.getInstance().getPostById(id));
	}
	
	private static void savePost(Post post) {
		try {
			PostService.getInstance().create(post);
			assertNotNull(post.getId());
		} catch (IllegalArgumentException e) {
			LOG.log(Level.WARNING, e.getMessage());
		} catch (HibernateException e) {
			LOG.log(Level.SEVERE, "Error saving new post: " + post.getContent());
			fail(e.getMessage());
		}
	}
}
