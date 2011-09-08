/**
 * 
 */
package br.com.lawbook.util.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.lawbook.model.Location;
import br.com.lawbook.model.Profile;
import br.com.lawbook.model.User;
import br.com.lawbook.util.DBMock;

/**
 * @author Edilson Luiz Ales Junior
 * @version 08SEP2011-01
 */

public class DBMockTest {

	@Ignore
	@Test
	public void testTableLocationGeneration() {
		DBMock db = DBMock.getInstance();
		List<Location> locations = db.getTableLocation();
		assertTrue(locations.size() == 5);
		for (int i = 0; i < locations.size(); i++) {
			System.out.println(locations.get(i).toString());
		}
	}
	
	@Test
	public void testTableUserGeneration() {
		
		Calendar c = Calendar.getInstance();
		System.out.println(c.getTimeInMillis());
//		DBMock db = DBMock.getInstance();
//		List<User> users = db.getTableUser();
//		assertTrue(users.size() == 5);
//		for (int i = 0; i < users.size(); i++) {
//			System.out.println(users.get(i).toString());
//		}
	}
	
	@Ignore
	@Test
	public void testTableProfileGeneration() {
		DBMock db = DBMock.getInstance();
		List<Profile> profiles = db.getTableProfile();
		assertTrue(profiles.size() == 5);
		for (int i = 0; i < profiles.size(); i++) {
			System.out.println(profiles.get(i).toString());
		}
	}

}
