package br.com.lawbook.dao;

import java.util.List;
import java.util.Map;

import br.com.lawbook.model.Post;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OCT2011-05
 * 
 */

public interface PostDAO extends GenericDAO<Post> {

	List<Post> getStreamPosts(Map<String,Object> attributes);
	
	List<Post> getProfileWall(Map<String,Object> attributes);

	Long getPostsCount();
}
