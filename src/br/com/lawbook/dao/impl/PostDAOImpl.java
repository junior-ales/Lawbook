package br.com.lawbook.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import br.com.lawbook.dao.PostDAO;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OUT2011-06 
 * 
 */
public class PostDAOImpl extends GenericDAOImpl<Post> implements PostDAO {

	public PostDAOImpl(Session session) {
		super(session);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getStreamPosts(final Map<String,Object> attributes){
		
		Profile profile = (Profile) attributes.get("profile");
		Integer first = (Integer) attributes.get("first");
		Integer pageSize = (Integer) attributes.get("pageSize");
		List<Long> sendersId = new ArrayList<Long>();
		
		for (Profile p : profile.getFriends()) {
			sendersId.add(p.getId());
		}
		Query query;
		String stringQuery = "select p from lwb_post p where ";
		
		if (sendersId.isEmpty()) {
			stringQuery += "sender_id = :myId or receiver_id = :myId order by datetime desc";
			query = this.getSession().createQuery(stringQuery);
		}
		else {
			stringQuery += "(sender_id in :sendersId and receiver_id = 0) or sender_id = :myId or receiver_id = :myId order by datetime desc";
			query = this.getSession().createQuery(stringQuery);
			query.setParameterList("sendersId", sendersId);
		}
		
		query.setParameter("myId", profile.getId());
		query.setFirstResult(first);
		query.setMaxResults(pageSize);
		
		return (List<Post>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getProfileWall(final Map<String,Object> attributes) {
		
		Long profileId = (Long) attributes.get("profileId");
		Integer first = (Integer) attributes.get("first");
		Integer pageSize = (Integer) attributes.get("pageSize");
		
		Query query = this.getSession().createQuery("from lwb_post where sender_id = :profileId and receiver_id = 0 order by datetime desc");
        query.setParameter("profileId", profileId);
        
		query.setFirstResult(first);
		query.setMaxResults(pageSize);
        
        return (List<Post>) query.list();
		
	}

	@Override
	public Long getPostsCount() {
		Criteria crit = this.getSession().createCriteria(Post.class);
		crit.setProjection(Projections.rowCount());
		return (Long) crit.list().get(0);	
	}
}

