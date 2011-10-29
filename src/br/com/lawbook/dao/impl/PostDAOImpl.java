package br.com.lawbook.dao.impl;

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
import br.com.lawbook.util.HibernateUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OUT2011-07 
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
	public List<Post> getStreamPosts(Long streamOwnerId, List<Long> sendersId, int first, int pageSize) throws HibernateException {
		Session session = HibernateUtil.getSession();
		LOG.info("Hibernate Session opened");
		Transaction tx = session.beginTransaction();
		
		try {
			Query query;
			StringBuilder stringQuery = new StringBuilder("select p from lwb_post p where ");
			
			if (sendersId.isEmpty()) {
				stringQuery.append("sender_id = :myId or receiver_id = :myId order by datetime desc");
				query = session.createQuery(stringQuery.toString());
				
			} else {
				stringQuery.append("(sender_id in :sendersId and receiver_id = 0) or sender_id = :myId or receiver_id = :myId order by datetime desc");
				query = session.createQuery(stringQuery.toString());
				query.setParameterList("sendersId", sendersId);
			}
			
			query.setParameter("myId", streamOwnerId);
			query.setFirstResult(first);
			query.setMaxResults(pageSize);
			
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
	public List<Post> getProfileWall(Long wallOwnerId, int first, int pageSize) throws HibernateException {
		Session session = HibernateUtil.getSession();
		LOG.info("Hibernate Session opened");
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery("from lwb_post where sender_id = :profileId and receiver_id = 0 order by datetime desc");
			query.setParameter("profileId", wallOwnerId);
			query.setFirstResult(first);
			query.setMaxResults(pageSize);

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
		Session session = HibernateUtil.getSession();
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
//		Session session = HibernateUtil.getSession();
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

