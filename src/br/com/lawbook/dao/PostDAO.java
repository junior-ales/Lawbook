package br.com.lawbook.dao;

import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Post;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-06
 * 
 */
public interface PostDAO {
	
	void create(Post post);
	
	void delete(Post post);

	List<Post> getStreamPosts(Long streamOwnerId, List<Long> sendersId, int first, int pageSize) throws HibernateException ;
	
	List<Post> getProfileWall(Long wallOwnerId, int first, int pageSize) throws HibernateException ;

	Long getPostsCount() throws HibernateException ;

}
