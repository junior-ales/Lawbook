package br.com.lawbook.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import br.com.lawbook.dao.PostDAO;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.HibernateUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OUT2011-06 
 * 
 */
public class PostDAOImpl implements PostDAO {
	
	private final static Logger LOG = Logger.getLogger("PostDAOImpl");
	
	@Override
	public void create(Post post) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete(Post post) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getStreamPosts(final HashMap<String, Object> attributes) throws HibernateException {

		Profile profile = (Profile) attributes.get("profile");
		Query query;
		StringBuilder stringQuery = new StringBuilder("select p from lwb_post p where ");

		Session session = HibernateUtil.getSessionFactory().openSession();
		LOG.info("Hibernate Session opened");
		Transaction tx = session.beginTransaction();
		try {
			if (!profile.getFriends().isEmpty()) {
				
				List<Long> sendersId = new ArrayList<Long>();
				for (Profile p : profile.getFriends()) sendersId.add(p.getId());
						
				stringQuery.append("(sender_id in :sendersId and receiver_id = 0) or sender_id = :myId or receiver_id = :myId order by datetime desc");
				query = session.createQuery(stringQuery.toString());
				query.setParameterList("sendersId", sendersId);
				
			} else {
				stringQuery.append("sender_id = :myId or receiver_id = :myId order by datetime desc");
				query = session.createQuery(stringQuery.toString());
			}
			
			query.setParameter("myId", profile.getId());
			query.setFirstResult((Integer) attributes.get("first"));
			query.setMaxResults((Integer) attributes.get("pageSize"));
			
			List<Post> posts = (List<Post>) query.list();
			tx.commit();
			return posts;
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			tx.rollback();
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getProfileWall(final HashMap<String, Object> attributes) throws HibernateException {

		Profile profile = (Profile) attributes.get("profile");

		Session session = HibernateUtil.getSessionFactory().openSession();
		LOG.info("Hibernate Session opened");
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery("from lwb_post where sender_id = :profileId and receiver_id = 0 order by datetime desc");
			query.setParameter("profileId", profile.getId());
			query.setFirstResult((Integer) attributes.get("first"));
			query.setMaxResults((Integer) attributes.get("pageSize"));

			List<Post> posts = (List<Post>) query.list();
			tx.commit();
			return posts;
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			tx.rollback();
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}

	@Override
	public Long getPostsCount() throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		LOG.info("Hibernate Session opened");
		Transaction tx = session.beginTransaction();
		try {
			Criteria crit = session.createCriteria(Post.class);
			crit.setProjection(Projections.rowCount());
			Long count = (Long) crit.list().get(0);
			tx.commit();
			return count;
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			tx.rollback();
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}
	
	// TODO
//	private void save(Post profile) throws HibernateException{
//		Session session = HibernateUtil.getSessionFactory().openSession();
//		LOG.info("Hibernate Session opened");
//		Transaction tx = session.beginTransaction();
//		try {
//			session.save(profile);
//			tx.commit();
//		} catch (Exception e) {
//			LOG.severe(e.getMessage());
//			tx.rollback();
//			throw new HibernateException(e);
//		} finally {
//			session.close();
//			LOG.info("Hibernate Session closed");
//		}
//	}
}

