package br.com.lawbook.DAO.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import br.com.lawbook.DAO.PostDAO;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 19OUT2011-04 
 * 
 */

public class HibernatePostDAO extends HibernateGenericDAO<Post> implements PostDAO {

	public HibernatePostDAO(Session session) {
		super(session);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getStreamPosts(HashMap<String,Object> attributes) {
		
		Profile profile = (Profile) attributes.get("profile");
		Integer first = (Integer) attributes.get("first");
		Integer pageSize = (Integer) attributes.get("pageSize");
		
		List<Long> sendersId = new ArrayList<Long>();
		for (Profile p : profile.getFriendsList()) {
			sendersId.add(p.getId());
		}
		
		StringBuilder stringQuery = new StringBuilder("select p from lwb_post p where "); 
		
		if (!sendersId.isEmpty()) {
			stringQuery.append("(sender_id in :sendersId and receiver_id = 0) or ");
		}
		stringQuery.append("sender_id = :myId or receiver_id = :myId order by datetime desc");
		
		Query query = this.getSession().createQuery(stringQuery.toString());
		query.setParameter("myId", profile.getId());
		
		if (!sendersId.isEmpty()) {
			query.setParameterList("sendersId", sendersId);
		}
		
		if (first == null || pageSize == null) {
			query.setMaxResults(8);
		}
		else {
			query.setFirstResult(first);
			query.setMaxResults(pageSize);
		}
		
		return (List<Post>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getProfileWall(Serializable profileId) {
		Query query = this.getSession().createQuery("from lwb_post where sender_id = :profileId and receiver_id = 0");
        query.setParameter("profileId", profileId);
        query.setMaxResults(10);
        return (List<Post>) query.list();
		
	}

	@Override
	public Long getPostsCount() {
		Criteria crit = this.getSession().createCriteria(Post.class);
		crit.setProjection(Projections.rowCount());
		return (Long) crit.list().get(0);	
	}
}

