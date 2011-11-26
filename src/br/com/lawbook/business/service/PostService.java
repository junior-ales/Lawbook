package br.com.lawbook.business.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.PostDAO;
import br.com.lawbook.dao.impl.PostDAOImpl;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 18NOV2011-08
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
	
	public void create(Post post) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(post, "PostService: save: post");
		post.setDateTime(Calendar.getInstance()); // when creating a new post, get the current timestamp
		PostDAO dao = new PostDAOImpl();
		dao.create(post);
	}
	
	public void delete(Post post) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(post, "PostService: delete: post");
		PostDAO dao = new PostDAOImpl();
		dao.delete(post);
	}
	
	public Post getPostById(Long postId) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(postId, "PostService: getPostById: postId");
		PostDAO dao = new PostDAOImpl();
		return dao.getPostById(postId);
	}

	public List<Post> getStream(Profile profile, int first, int pageSize) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(profile, "PostService: getStream: profile");
		JavaUtil.validateParameter(first, "PostService: getStream: first");
		JavaUtil.validateParameter(pageSize, "PostService: getStream: pageSize");
		
		List<Long> sendersId = new ArrayList<Long>();
		for (Profile p : profile.getFriends()) sendersId.add(p.getId());
		
		PostDAO dao = new PostDAOImpl();
		return dao.getStreamPosts(profile.getId(), sendersId, first, pageSize);
	}

	public List<Post> getWall(Profile profileOwner, Profile authProfile, int first, int pageSize) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(profileOwner, "PostService: getWall: profileOwner");
		JavaUtil.validateParameter(authProfile, "PostService: getWall: authProfile");
		JavaUtil.validateParameter(first, "PostService: getWall: first");
		JavaUtil.validateParameter(pageSize, "PostService: getWall: pageSize");
		
		PostDAO dao = new PostDAOImpl();
		return dao.getProfileWall(profileOwner.getId(), authProfile.getId(), first, pageSize);
	}
	
	public int getPostsCount() throws HibernateException {
		PostDAO dao = new PostDAOImpl();
		return Integer.parseInt(dao.getPostsCount().toString());
	}
}