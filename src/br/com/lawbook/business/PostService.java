package br.com.lawbook.business;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.DAO.PostDAO;
import br.com.lawbook.model.Post;

/**
 * @author Edilson Luiz Ales Junior
 * @version 19OCT2011-04
 * 
 */
public class PostService implements Serializable{
	
	private static PostService instance;
	private static final long serialVersionUID = 5170825358674011787L;

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
		return dao.getStreamPosts(attributes);
	}

	public List<Post> getWall(HashMap<String,Object> attributes) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		PostDAO dao = factory.getPostDAO();
		return dao.getProfileWall(attributes);
	}
	
	public int getPostsCount() {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		PostDAO dao = factory.getPostDAO();
		return Integer.parseInt(dao.getPostsCount().toString());
	}
}