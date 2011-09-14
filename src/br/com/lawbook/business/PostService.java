package br.com.lawbook.business;

import java.util.List;

import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.DAO.PostDAO;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 14SEP2011-01
 * 
 */
public class PostService {
	
	private static PostService instance;

	private PostService() {
	}
	
	public static PostService getInstance() {
		if (instance == null) {
			instance = new PostService();
		}
		return instance;
	}
	
	public void save(Post post) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		PostDAO dao = factory.getPostDAO();
		factory.beginTx();
		dao.save(post);
		factory.shutTx();
	}
	
	public void delete(Post post) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		PostDAO dao = factory.getPostDAO();
		factory.beginTx();
		dao.delete(post);
		factory.shutTx();
	}

	public List<Post> getStream(List<Profile> friendsList) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		PostDAO dao = factory.getPostDAO();
		List<Post> streamPosts = dao.getStreamPosts(friendsList);
		return streamPosts;
	}

	public List<Post> getWall(Long profileId) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		PostDAO dao = factory.getPostDAO();
		List<Post> profileWall = dao.getProfileWall(profileId);
		return profileWall;
	}
}