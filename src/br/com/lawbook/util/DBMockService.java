package br.com.lawbook.util;

import java.util.Calendar;

import br.com.lawbook.model.Comment;
import br.com.lawbook.model.Location;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 07SEP2011-01 
 * 
 */

public class DBMockService {

	private DBMock db;
	
	protected User getNewUser(Long userId) {
		User user = new User();
		user.setId(userId);
		user.setUserName("user-" + userId);
		user.setPassword("passUser-" + userId);
		user.setEmail("emailUser-" + userId + "-@mail.com");
		user.setProfile(getNewProfile(userId + 10L));
		return user;
	}

	protected Profile getNewProfile(Long profileId) {
		Profile profile = new Profile();
		Calendar c = Calendar.getInstance();
		
		profile.setId(profileId);
		profile.setFirstName("FN-" + profileId + "-Profile");
		profile.setLastName("LN-" + profileId + "-Profile");
		profile.setBirth(c);
		profile.setLocation(this.getLocationById(profileId * 10L));
		profile.setAboutMe("I'm the profile " + profileId + ". What great profile test ahn?");
		profile.setWall(null);
		profile.setFriendsList(null);
		profile.setStream(null);
		return profile;
	}

	protected Post getNewPost(Long postId) {
		Post post = new Post();
		post.setId(postId);
		post.setSenderId(this.getUserById(postId / 100L));
		post.setReceiversId(null);
		post.setContent(null);
		post.setDateTime(null);
		post.setComments(null);
		return post;
	}

	protected Comment getNewComment(Long commentId) {
		Comment comment = new Comment();
		return comment;
	}

	protected Location getNewLocation(Long locationId) {
		Location location = new Location();
		location.setId(locationId);
		location.setCountry("Country-"+locationId);
		location.setState("State-"+locationId);
		location.setCity("City-"+locationId);
		location.setMainAdd("MainAddress-"+locationId);
		location.setMainZipCode("00000-"+locationId);
		location.setSecAdd("SecAddress-"+locationId);
		location.setSecAddZipCode("11111-"+locationId);
		return location;
	}

	protected User getUserById(Long userId) {
		for (User u : this.db.getTableUser()) {
			if (u.getId() == userId) {
				return u;
			}
		}
		return null;
	}
	
	protected Location getLocationById(Long locationId) {
		db = DBMock.getInstance();
		for (Location l : this.db.getTableLocation()) {
			if (l.getId() == locationId) {
				return l;
			}
		}
		return null;
	}
	
	protected Profile getProfileById(Long profileId) {
		db = DBMock.getInstance();
		for (Profile p : this.db.getTableProfile()) {
			if (p.getId() == profileId) {
				return p;
			}
		}
		return null;
	}
	
}
