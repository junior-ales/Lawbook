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
 * @version 09SEP2011-05
 * 
 */
public class DBMock {

	private static DBMock instance;
	private List<User> tableUser;
	private List<Profile> tableProfile;
	private List<Post> tablePost;
	private List<Comment> tableComment;
	private List<Location> tableLocation;

	public static DBMock getInstance() {
		if (instance == null) {
			instance = new DBMock();
		}
		return instance;
	}
	
	private DBMock() {
		tableLocation = new ArrayList<Location>();
		tableUser = new ArrayList<User>();
		tableProfile = new ArrayList<Profile>();
		tableComment = new ArrayList<Comment>();
		tablePost = new ArrayList<Post>();
		generateTables();
		applyDependencies(); 
	}

	private void generateTables() {
		DBMockPopulate dbp = new DBMockPopulate(tableUser, tableProfile, tablePost, tableComment, tableLocation);
		tableLocation = dbp.generateTableLocation();
		tableUser = dbp.generateTableUser();
		tableProfile = dbp.generateTableProfile();
		tableComment = dbp.generateTableComment();
		tablePost = dbp.generateTablePost();
	}
	
	private void applyDependencies() {
		//DBMockService dbs = new DBMockService(tableUser, tableProfile, tablePost, tableComment, tableLocation);
		
	}

	public List<User> getTableUser() {
		return tableUser;
	}

	public void setTableUser(List<User> tableUser) {
		this.tableUser = tableUser;
	}

	public List<Profile> getTableProfile() {
		return tableProfile;
	}

	public void setTableProfile(List<Profile> tableProfile) {
		this.tableProfile = tableProfile;
	}

	public List<Post> getTablePost() {
		return tablePost;
	}

	public void setTablePost(List<Post> tablePost) {
		this.tablePost = tablePost;
	}

	public List<Comment> getTableComment() {
		return tableComment;
	}

	public void setTableComment(List<Comment> tableComment) {
		this.tableComment = tableComment;
	}

	public List<Location> getTableLocation() {
		return tableLocation;
	}

	public void setTableLocation(List<Location> tableLocation) {
		this.tableLocation = tableLocation;
	}
	
}
