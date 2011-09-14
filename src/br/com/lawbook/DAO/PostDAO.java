package br.com.lawbook.DAO;

import java.io.Serializable;
import java.util.List;

import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 14SEP2011-01
 * 
 */

public interface PostDAO extends GenericDAO<Post> {

	List<Post> getStreamPosts(List<Profile> friendsList);
	
	List<Post> getProfileWall(Serializable profileId);
}
