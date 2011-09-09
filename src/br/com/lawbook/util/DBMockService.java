package br.com.lawbook.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
@SuppressWarnings("unused")
public class DBMockService {

	private DBMock db;
	private List<User> tableUser;
	private List<Profile> tableProfile;
	private List<Post> tablePost;
	private List<Comment> tableComment;
	private List<Location> tableLocation;
	
	public DBMockService(List<User> tableUser, List<Profile> tableProfile, List<Post> tablePost, List<Comment> tableComment, List<Location> tableLocation) {
		this.tableUser = tableUser;
		this.tableProfile = tableProfile;
		this.tablePost = tablePost;
		this.tableComment = tableComment;
		this.tableLocation = tableLocation;
	}

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
		profile.setId(profileId);
		profile.setFirstName("FN-" + profileId + "-Profile");
		profile.setLastName("LN-" + profileId + "-Profile");
		profile.setBirth(newDate(profileId));
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
		post.setSenderId(this.getUserById(postId / 1000L));
		post.setReceiversId(null);
		post.setContent("Post content bla bla bla bla bla - " + postId);
		post.setDateTime(newDate(postId));
		post.setComments(null);
		return post;
	}

	protected Comment getNewComment(Long commentId) {
		Comment comment = new Comment();
		comment.setId(commentId);
		comment.setSenderId(this.getUserById(commentId / 10000L));
		comment.setContent("Comment content bla bla bla bla bla - " + commentId);
		comment.setDateTime(newDate(commentId));
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
		for (Location l : this.tableLocation) {
			if (l.getId() == locationId) {
				return l;
			}
		}
		return null;
	}
	
	public Post getPostById(Long postId) {
		for (Post p : this.tablePost) {
			if (p.getId() == postId) {
				return p;
			}
		}
		return null;
	}
	
	public Comment getCommentById(Long commentId) {
		for (Comment c : this.tableComment) {
			if (c.getId() == commentId) {
				return c;
			}
		}
		return null;
	}
	
	private Calendar newDate(Long objId) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			c.setTime(df.parse("01/08/1988"));
			c.add(Calendar.DAY_OF_YEAR, objId.intValue());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c;
	}
	
}
