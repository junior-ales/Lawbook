package br.com.lawbook.util;

import java.util.ArrayList;
import java.util.List;

import br.com.lawbook.model.*;

/**
 * @author Edilson Luiz Ales Junior
 * @version 06SEP2011-02
 * 
 */
@SuppressWarnings("unused")
public class DBMockService {
	
	public DBMockService() {
	}

	protected List<User> generateTableUser(List<User> tableUser) {
		if (tableUser == null || tableUser.isEmpty()) {
			tableUser = new ArrayList<User>();
		}
		tableUser.add(getNewUser(1L));
		tableUser.add(getNewUser(2L));
		tableUser.add(getNewUser(3L));
		tableUser.add(getNewUser(4L));
		tableUser.add(getNewUser(5L));
		return tableUser;
	}

	protected List<Profile> generateTableProfile(List<User> tableUser) {
		if (tableUser == null || tableUser.isEmpty()) {
			tableUser = generateTableUser(tableUser);
		}
		List<Profile> tableProfile = new ArrayList<Profile>();
		for(User u : tableUser) {
			tableProfile.add(getNewProfile(u.getId() + 10L));
		}
		return tableProfile;
	}

	protected List<Post> generateTablePost() {
		List<Post> tablePost = new ArrayList<Post>();
		tablePost.add(getNewPost(100L, 1L));
		tablePost.add(getNewPost(200L, 2L));
		tablePost.add(getNewPost(300L, 3L));
		tablePost.add(getNewPost(400L, 4L));
		tablePost.add(getNewPost(500L, 5L));
		return tablePost;
	}

	protected List<Comment> generateTableComment() {
		List<Comment> tableComment = new ArrayList<Comment>();
		return tableComment;
	}
	
	protected List<Location> generateTableLocation() {
		List<Location> tableLocation = new ArrayList<Location>();
		return tableLocation;
	}

	private User getNewUser(Long userId) {
		User user = new User();
		user.setId(userId);
		user.setUserName("user-" + userId);
		user.setPassword("passUser-" + userId);
		user.setEmail("emailUser-" + userId + "-@mail.com");
		user.setPerfil(getNewProfile(userId + 10L));
		return user;
	}

	private Profile getNewProfile(Long profileId) {
		Profile profile = new Profile();
		profile.setId(profileId); 
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
		Comment comment = new Comment();
		return comment;
	}
	
	private Location getNewLocation(Long locationId) {
		Location location = new Location();
		return location;
	}
	
}
