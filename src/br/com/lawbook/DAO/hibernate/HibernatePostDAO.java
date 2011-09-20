package br.com.lawbook.DAO.hibernate;

import java.io.Serializable;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;

import br.com.lawbook.DAO.PostDAO;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 14SEP2011-01 
 * 
 */

public class HibernatePostDAO extends HibernateGenericDAO<Post> implements PostDAO {

	public HibernatePostDAO(Session session) {
		super(session);
	}

	@Override
	public List<Post> getStreamPosts(List<Profile> friendsList) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getProfileWall(Serializable profileId) {
		Query query = this.getSession().createQuery("from lwb_post where sender_id = :profileId and receiver_id = 0");
        query.setParameter("profileId", profileId);
        query.setMaxResults(10);
        return (List<Post>) query.list();
		
	}
}

