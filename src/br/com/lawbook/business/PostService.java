package br.com.lawbook.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.PostDAO;
import br.com.lawbook.dao.impl.PostDAOImpl;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-06
 * 
 */
public final class PostService implements Serializable{
	
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
	
	public void save(Post post) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(post);
		PostDAO dao = new PostDAOImpl();
		dao.create(post);
	}
	
	public void delete(Post post) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(post);
		PostDAO dao = new PostDAOImpl();
		dao.delete(post);
	}

	public List<Post> getStream(HashMap<String,Object> attributes) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(attributes);
		Profile profile = (Profile) attributes.get("profile");
		Integer first = (Integer) attributes.get("first");
		Integer pageSize = (Integer) attributes.get("pageSize");
		JavaUtil.validateParameter(profile);
		JavaUtil.validateParameter(first);
		JavaUtil.validateParameter(pageSize);
		
		List<Long> sendersId = new ArrayList<Long>();
		for (Profile p : profile.getFriends()) sendersId.add(p.getId());
		
		PostDAO dao = new PostDAOImpl();
		return dao.getStreamPosts(profile.getId(), sendersId, first, pageSize);
	}

	public List<Post> getWall(HashMap<String,Object> attributes) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(attributes);
		Profile profile = (Profile) attributes.get("profile");
		Integer first = (Integer) attributes.get("first");
		Integer pageSize = (Integer) attributes.get("pageSize");
		JavaUtil.validateParameter(profile);
		JavaUtil.validateParameter(first);
		JavaUtil.validateParameter(pageSize);
		
		List<Long> sendersId = new ArrayList<Long>();
		for (Profile p : profile.getFriends()) sendersId.add(p.getId());
		
		PostDAO dao = new PostDAOImpl();
		return dao.getProfileWall(profile.getId(), first, pageSize);
	}
	
	public int getPostsCount() throws HibernateException {
		PostDAO dao = new PostDAOImpl();
		return Integer.parseInt(dao.getPostsCount().toString());
	}
}