/**
 * 
 */
package br.com.lawbook.util;

import java.util.ArrayList;
import java.util.List;

import br.com.lawbook.model.Comment;
import br.com.lawbook.model.Location;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 06SEP2011-01
 * 
 */
@SuppressWarnings("unused")
public class DBMock {

	private static DBMock instance;
	private List<User> tableUser = new ArrayList<User>();
	private List<Profile> tableProfile = new ArrayList<Profile>();
	private List<Post> tablePost = new ArrayList<Post>();
	private List<Comment> tableComment = new ArrayList<Comment>();
	private List<Location> tableLocation = new ArrayList<Location>();

	public static DBMock getInstance() {
		if (instance == null) {
			instance = new DBMock();
		}
		return instance;
	}

	private List<User> getTableUser() {
		this.tableUser.add(getNewUser(10L));
		this.tableUser.add(getNewUser(20L));
		this.tableUser.add(getNewUser(30L));
		this.tableUser.add(getNewUser(40L));
		this.tableUser.add(getNewUser(50L));
		return this.tableUser;
	}

	private List<Profile> getTableProfile() {
//		if (this.tableUser == null || this.tableUser.isEmpty()) {
//			this.tableUser = this.getTableUser();
//		}
//		for (User u : this.tableUser) {
//			this.tableProfile.add(getNewProfile(u.getId()));
//		}
		return this.tableProfile;
	}

	private List<Post> getTablePost() {
		this.tablePost.add(getNewPost(100L, 10L));
		this.tablePost.add(getNewPost(200L, 20L));
		this.tablePost.add(getNewPost(300L, 30L));
		this.tablePost.add(getNewPost(400L, 40L));
		this.tablePost.add(getNewPost(500L, 50L));
		this.tablePost.add(getNewPost(600L, 60L));
		return this.tablePost;
	}

	private List<Comment> getTableComment() {
		return this.tableComment;
	}
	
	private List<Location> getTableLocation() {
		return this.tableLocation;
	}

	private void setTableUser(List<User> tableUser) {
		this.tableUser = tableUser;
	}

	private void setTableProfile(List<Profile> tableProfile) {
		this.tableProfile = tableProfile;
	}

	private void setTablePost(List<Post> tablePost) {
		this.tablePost = tablePost;
	}
	
	private void setTableComment(List<Comment> tableComment) {
		this.tableComment = tableComment;
	}
	
	private void setTableLocation(List<Location> tableLocation) {
		this.tableLocation = tableLocation;
	}

	private User getNewUser(Long userId) {
		User user = new User();
		user.setId(userId);
		user.setUserName("user-" + userId);
		user.setPassword("passUser-" + userId);
		user.setEmail("emailUser-" + userId + "-@mail.com");
		user.setPerfil(this.getNewProfile(userId));
		return user;
	}

	private Profile getNewProfile(Long profileId) {
		Profile profile = new Profile();
		profile.setId(profileId); // profile id will be always equals to user id, but just for tests
		profile.setFirstName("FN-" + profileId + "-Profile");
		profile.setLastName("LN-" + profileId + "-Profile");
		profile.setBirth(null);
		profile.setLocation(null);
		profile.setAboutMe("I'm the profile " + profileId + ". What great profile test ahn?");
		profile.setWall(null);
		profile.setFriendsList(null);
		profile.setStream(null);
		return profile;
	}
	
	private Post getNewPost(Long postId, Long userId) {
		Post post = new Post();
		post.setId(postId);
		post.setSenderId(null);
		post.setReceiversId(null);
		post.setContent(null);
		post.setDateTime(null);
		post.setComments(null);
		return post;
	}
	
	private Comment getNewComment(Long commentId) {
		return null;
	}
	
	private Location getNewLocation(Long locationId) {
		return null;
	}
	
	

}
