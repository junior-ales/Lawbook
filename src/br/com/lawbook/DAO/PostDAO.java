package br.com.lawbook.DAO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import br.com.lawbook.model.Post;

/**
 * @author Edilson Luiz Ales Junior
 * @version 18OCT2011-03
 * 
 */

public interface PostDAO extends GenericDAO<Post> {

	List<Post> getStreamPosts(HashMap<String,Object> attributes);
	
	List<Post> getProfileWall(Serializable profileId);

	Long getPostsCount();
}
