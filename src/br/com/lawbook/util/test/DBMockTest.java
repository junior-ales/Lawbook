/**
 * 
 */
package br.com.lawbook.util.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.com.lawbook.model.*;
import br.com.lawbook.util.DBMock;
import br.com.lawbook.util.DBMockService;

/**
 * @author Edilson Luiz Ales Junior
 * @version 08SEP2011-01
 */

public class DBMockTest {

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
	
	@Test
	public void getUserByIdTest() {
		DBMock db = DBMock.getInstance();
		DBMockService dbs = new DBMockService(db.getTableUser(), db.getTableProfile(), 
				db.getTablePost(), db.getTableComment(), db.getTableLocation());
		assertNotNull(dbs.getUserById(1L));
	}
	
	@Test
	public void getProfileByIdTest() {
		DBMock db = DBMock.getInstance();
		DBMockService dbs = new DBMockService(db.getTableUser(), db.getTableProfile(), 
				db.getTablePost(), db.getTableComment(), db.getTableLocation());
		assertNotNull(dbs.getProfileById(10L));
	}
	
	@Test
	public void getLocationByIdTest() {
		DBMock db = DBMock.getInstance();
		DBMockService dbs = new DBMockService(db.getTableUser(), db.getTableProfile(), 
				db.getTablePost(), db.getTableComment(), db.getTableLocation());
		assertNotNull(dbs.getLocationById(100L));
	}
	
	@Test
	public void getPostByIdTest() {
		DBMock db = DBMock.getInstance();
		DBMockService dbs = new DBMockService(db.getTableUser(), db.getTableProfile(), 
				db.getTablePost(), db.getTableComment(), db.getTableLocation());
		assertNotNull(dbs.getPostById(1000L));
	}
	
	@Test
	public void getCommentByIdTest() {
		DBMock db = DBMock.getInstance();
		DBMockService dbs = new DBMockService(db.getTableUser(), db.getTableProfile(), 
				db.getTablePost(), db.getTableComment(), db.getTableLocation());
		assertNotNull(dbs.getCommentById(10000L));
	}
}
