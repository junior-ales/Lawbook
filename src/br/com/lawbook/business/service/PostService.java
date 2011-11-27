package br.com.lawbook.business.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.PostDAO;
import br.com.lawbook.dao.impl.PostDAOImpl;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27NOV2011-09
 *
 */
public final class PostService implements Serializable{

	private final PostDAO dao;
	private static PostService instance;
	private static final long serialVersionUID = -5158354733440897922L;

	private PostService() {
		this.dao = new PostDAOImpl();
	}

	public static PostService getInstance() {
		if (instance == null) {
			instance = new PostService();
		}
		return instance;
	}

	public void create(final Post post) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(post, "PostService: save: post");
		this.dao.create(post);
	}

	public void delete(final Post post) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(post, "PostService: delete: post");
		this.dao.delete(post);
	}

	public Post getPostById(final Long postId) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(postId, "PostService: getPostById: postId");
		return this.dao.getPostById(postId);
	}

	public List<Post> getStream(final Profile profile, final int first, final int pageSize) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(profile, "PostService: getStream: profile");
		JavaUtil.validateParameter(first, "PostService: getStream: first");
		JavaUtil.validateParameter(pageSize, "PostService: getStream: pageSize");

		final List<Long> sendersId = new ArrayList<Long>();
		for (final Profile p : profile.getFriends()) sendersId.add(p.getId());

		return this.dao.getStreamPosts(profile.getId(), sendersId, first, pageSize);
	}

	public List<Post> getWall(final Profile profileOwner, final Profile authProfile, final int first, final int pageSize) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(profileOwner, "PostService: getWall: profileOwner");
		JavaUtil.validateParameter(authProfile, "PostService: getWall: authProfile");
		JavaUtil.validateParameter(first, "PostService: getWall: first");
		JavaUtil.validateParameter(pageSize, "PostService: getWall: pageSize");

		return this.dao.getProfileWall(profileOwner.getId(), authProfile.getId(), first, pageSize);
	}

	public int getPostsCount() throws HibernateException {
		return Integer.parseInt(this.dao.getPostsCount().toString());
	}
}