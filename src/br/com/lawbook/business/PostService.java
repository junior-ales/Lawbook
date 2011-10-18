package br.com.lawbook.business;

import java.util.HashMap;
import java.util.List;

import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.DAO.PostDAO;
import br.com.lawbook.model.Post;

/**
 * @author Edilson Luiz Ales Junior
 * @version 18OCT2011-03
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

	public List<Post> getStream(HashMap<String,Object> attributes) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		PostDAO dao = factory.getPostDAO();
		List<Post> streamPosts = dao.getStreamPosts(attributes);
		return streamPosts;
	}

	public List<Post> getWall(Long profileId) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		PostDAO dao = factory.getPostDAO();
		List<Post> profileWall = dao.getProfileWall(profileId);
		return profileWall;
	}
	
	public int getPostsCount() {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		PostDAO dao = factory.getPostDAO();
		return Integer.parseInt(dao.getPostsCount().toString());
	}
}