package br.com.lawbook.model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 13SEP2011-02 
 * 
 */

public class ProfileTest {

	@Test(expected=IllegalArgumentException.class)
	public void testSetBirth() {

		Profile profile = new Profile();
		Calendar day = Calendar.getInstance();
		
		// testing passing the day of tomorrow as parameter
		day.add(Calendar.DAY_OF_MONTH, 1);
		profile.setBirth(day);

	}
	
	@Test
	public void testFriendship() {
		List<Profile> friendsListMe = new ArrayList<Profile>();
		List<Profile> friendsListBuddy = new ArrayList<Profile>();
		Profile me = new Profile();
		Profile buddy = new Profile();
		me.setFirstName("Jr");
		buddy.setFirstName("Darlei");
		
		friendsListMe.add(buddy);
		me.setFriendsList(friendsListMe);
		
		friendsListBuddy.add(me);
		buddy.setFriendsList(friendsListBuddy);

		assertTrue(me.getFriendsList().contains(buddy) && buddy.getFriendsList().contains(me));
	}
}
