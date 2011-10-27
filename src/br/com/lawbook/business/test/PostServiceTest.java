package br.com.lawbook.business.test;

import java.util.Calendar;

import org.junit.Test;

import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Post;

/**
 * @author Edilson Luiz Ales Junior
 * @version 26OUT2011-04
 * 
 */
public class PostServiceTest {
	
	@Test
	public void getStreamTest() {
		ProfileService profileService = ProfileService.getInstance();
		Post post = new Post();
		post.setContent("First comment");
		post.setDateTime(Calendar.getInstance());
		post.setReceiver(profileService.getProfileById(0L));
		post.setSender(profileService.getProfileByUserName("admin"));
	}
}
