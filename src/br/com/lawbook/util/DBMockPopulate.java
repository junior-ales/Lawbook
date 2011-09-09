package br.com.lawbook.util;

import java.util.List;

import br.com.lawbook.model.Comment;
import br.com.lawbook.model.Location;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 07SEP2011-04
 * 
 */

public class DBMockPopulate {

	private DBMockService dbs;
	private final static Long TABLE_SIZE = 5L;
	private List<User> tableUser;
	private List<Profile> tableProfile;
	private List<Post> tablePost;
	private List<Comment> tableComment;
	private List<Location> tableLocation;

	public DBMockPopulate(List<User> tableUser, List<Profile> tableProfile,
			List<Post> tablePost, List<Comment> tableComment,
			List<Location> tableLocation) {
		this.tableUser = tableUser;
		this.tableProfile = tableProfile;
		this.tablePost = tablePost;
		this.tableComment = tableComment;
		this.tableLocation = tableLocation;
		this.dbs = new DBMockService(this.tableUser, this.tableProfile, this.tablePost,
				this.tableComment, this.tableLocation);
	}

	public List<User> generateTableUser() {
		for (Long i = 1L; i <= TABLE_SIZE; i++) {
			tableUser.add(dbs.getNewUser(i));
		}
		return tableUser;
	}

	public List<Profile> generateTableProfile() {
		User u = new User();
		for (int i = 0; i < this.tableUser.size(); i++) {
			u = this.tableUser.get(i);
			tableProfile.add(u.getProfile());
		}
		return tableProfile;
	}

	public List<Location> generateTableLocation() {
		for (Long i = 1L; i <= TABLE_SIZE; i++) {
			tableLocation.add(dbs.getNewLocation(i * 100L));
		}
		return tableLocation;
	}

	public List<Post> generateTablePost() {
		for (Long i = 1L; i <= TABLE_SIZE; i++) {
			tablePost.add(dbs.getNewPost(i * 1000L));
		}
		return tablePost;
	}

	public List<Comment> generateTableComment() {
		for (Long i = 1L; i <= TABLE_SIZE; i++) {
			tableComment.add(dbs.getNewComment(i * 10000L));
		}
		return tableComment;
	}
}
