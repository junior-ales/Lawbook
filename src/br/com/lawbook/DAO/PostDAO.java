package br.com.lawbook.DAO;

import java.io.Serializable;
import java.util.List;

import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 15OCT2011-02
 * 
 */

public interface PostDAO extends GenericDAO<Post> {

	List<Post> getStreamPosts(Profile profile);
	
	List<Post> getProfileWall(Serializable profileId);
}
