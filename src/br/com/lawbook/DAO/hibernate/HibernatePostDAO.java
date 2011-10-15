package br.com.lawbook.DAO.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;

import br.com.lawbook.DAO.PostDAO;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 15OUT2011-02 
 * 
 */

public class HibernatePostDAO extends HibernateGenericDAO<Post> implements PostDAO {

	public HibernatePostDAO(Session session) {
		super(session);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getStreamPosts(Profile profile) {
		
		List<Long> sendersId = new ArrayList<Long>();
		for (Profile p : profile.getFriendsList()) {
			sendersId.add(p.getId());
		}
		sendersId.add(profile.getId());
		
		String stringQuery = "select p from lwb_post p where sender_id in :sendersId";
		Query query = this.getSession().createQuery(stringQuery);
		query.setParameterList("sendersId", sendersId);
		query.setMaxResults(8);
		return (List<Post>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getProfileWall(Serializable profileId) {
		Query query = this.getSession().createQuery("from lwb_post where sender_id = :profileId and receiver_id = 0");
        query.setParameter("profileId", profileId);
        query.setMaxResults(8);
        return (List<Post>) query.list();
		
	}
}

