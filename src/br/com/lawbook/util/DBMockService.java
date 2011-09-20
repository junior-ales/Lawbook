package br.com.lawbook.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import br.com.lawbook.model.*;

/**
 * @author Edilson Luiz Ales Junior
 * @version 12SEP2011-03 
 * 
 */

@SuppressWarnings("unused")
public class DBMockService {

	private List<User> tableUser;
	private List<Profile> tableProfile;
	private List<Post> tablePost;
	private List<Comment> tableComment;
	private List<Location> tableLocation;
	
	public DBMockService() {
	}
	
	public DBMockService(List<User> tableUser, List<Profile> tableProfile, List<Post> tablePost, List<Comment> tableComment, List<Location> tableLocation) {
		this.tableUser = tableUser;
		this.tableProfile = tableProfile;
		this.tablePost = tablePost;
		this.tableComment = tableComment;
		this.tableLocation = tableLocation;
	}

	public User getNewUser(Long userId) {
		User user = new User();
		user.setId(userId);
		user.setUserName("user-" + userId);
		user.setPassword("passUser-" + userId);
		user.setEmail("emailUser-" + userId + "-@mail.com");
		return user;
	}

	public Profile getNewProfile(Long profileId) {
		Profile profile = new Profile();
		profile.setId(profileId);
		profile.setUserOwner(getUserById(profileId / 10L));
		profile.setFirstName("FN-" + profileId + "-Profile");
		profile.setLastName("LN-" + profileId + "-Profile");
		profile.setBirth(this.newDate(profileId));
		profile.setLocation(new Location());
		profile.setAboutMe("I'm the profile " + profileId + ". What great profile test ahn?");
		profile.setFriendsList(new ArrayList<Profile>());
		return profile;
	}

	public Post getNewPost(Long postId) {
		Post post = new Post();
		post.setId(postId);
		post.setSender(new Profile());
//		post.setReceiver(new ArrayList<Profile>());
		post.setContent("Post content bla bla bla bla bla - " + postId);
		post.setDateTime(this.newDate(postId));
		post.setComments(new ArrayList<Comment>());
		return post;
	}

	public Comment getNewComment(Long commentId) {
		Comment comment = new Comment();
		comment.setId(commentId);
		comment.setSender(new Profile());
		comment.setContent("Comment content bla bla bla bla bla - " + commentId);
		comment.setDateTime(this.newDate(commentId));
		return comment;
	}

	public Location getNewLocation(Long locationId) {
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

	private Long newId() {
		Long id = new Random().nextLong();
		if (id < 0) {
			id = id * -1L;
		}
		return id % 10000L;
	}
	
	public User getUserById(Long userId) {
		for (User u : this.tableUser) {
			if (u.getId() == userId) {
				return u;
			}
		}
		return null;
	}
	public Profile getProfileById(Long profileId) {
		
		for (Profile p : this.tableProfile) {
			if (p.getId() == profileId) {
				return p;
			}
		}
		return null;
	}
	
	public Location getLocationById(Long locationId) {
		List<Location> locations = this.tableLocation;
		for (Location l : locations) {
			if (l.getId() == locationId) {
				return l;
			}
		}
		return null;
	}
	
	public Post getPostById(Long postId) {
		List<Post> posts = this.tablePost;
		for (Post p : posts) {
			if (p.getId() == postId) {
				System.out.println(p.toString());
				return p;
			}
		}
		return null;
	}
	
	public Comment getCommentById(Long commentId) {
		List<Comment> comments = this.tableComment;
		for (Comment c : comments) {
			if (c.getId().equals(commentId)) {
				return c;
			}
		}
		return null;
	}
	
	private Calendar newDate(Long objId) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			c.setTime(df.parse("02/08/1988"));
			if(objId > 500) {
				objId = (objId * 10L) / 100L;
			}
			c.add(Calendar.DAY_OF_YEAR, objId.intValue());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c;
	}
	
}
