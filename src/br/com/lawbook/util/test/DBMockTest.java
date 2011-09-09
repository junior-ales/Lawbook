/**
 * 
 */
package br.com.lawbook.util.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.com.lawbook.model.Comment;
import br.com.lawbook.model.Location;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.model.User;
import br.com.lawbook.util.DBMock;

/**
 * @author Edilson Luiz Ales Junior
 * @version 08SEP2011-01
 */

public class DBMockTest {
	
	
	public DBMockTest() {
		
	}
	
	@Test
	public void tableUserGenerationTest() {
		DBMock db = DBMock.getInstance();
		List<User> users = db.getTableUser();
		assertTrue(users.size() == 5);
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).toString());
		}
	}
	
	@Test
	public void tableProfileGenerationTest() {
		DBMock db = DBMock.getInstance();
		List<Profile> profiles = db.getTableProfile();
		assertTrue(profiles.size() == 5);
		for (int i = 0; i < profiles.size(); i++) {
			System.out.println(profiles.get(i).toString());
		}
	}
	
	@Test
	public void tableLocationGenerationTest() {
		DBMock db = DBMock.getInstance();
		List<Location> locations = db.getTableLocation();
		assertTrue(locations.size() == 5);
		for (int i = 0; i < locations.size(); i++) {
			System.out.println(locations.get(i).toString());
		}
		
	}
	
	@Test
	public void tablePostGenerationTest() {
		DBMock db = DBMock.getInstance();
		List<Post> posts = db.getTablePost();
		assertTrue(posts.size() == 5);
		for (int i = 0; i < posts.size(); i++) {
			System.out.println(posts.get(i).toString());
		}
	}
	
	@Test
	public void tableCommentGenerationTest() {
		DBMock db = DBMock.getInstance();
		List<Comment> comments = db.getTableComment();
		assertTrue(comments.size() == 5);
		for (int i = 0; i < comments.size(); i++) {
			System.out.println(comments.get(i).toString());
		}
	}

	
	
}
