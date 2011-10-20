package br.com.lawbook.DAO;

import java.util.HashMap;
import java.util.List;

import br.com.lawbook.model.Post;

/**
 * @author Edilson Luiz Ales Junior
 * @version 19OCT2011-04
 * 
 */

public interface PostDAO extends GenericDAO<Post> {

	List<Post> getStreamPosts(HashMap<String,Object> attributes);
	
	List<Post> getProfileWall(HashMap<String,Object> attributes);

	Long getPostsCount();
}
