package br.com.lawbook.dao.impl;

import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.lawbook.dao.CommentDAO;
import br.com.lawbook.model.Comment;
import br.com.lawbook.util.HibernateUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-02 
 * 
 */
public class CommentDAOImpl implements CommentDAO {
	
	private final static Logger LOG = Logger.getLogger("CommentDAOImpl");

	@Override
	public void save(Comment comment) throws HibernateException {
		Session session = HibernateUtil.getSession();
		LOG.info("Hibernate Session opened");
		Transaction tx = session.beginTransaction();
		try {
			session.save(comment);
			tx.commit();
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
	public void delete(Comment comment) throws HibernateException {
		Session session = HibernateUtil.getSession();
		LOG.info("Hibernate Session opened");
		Transaction tx = session.beginTransaction();
		try {
			session.delete(comment);
			tx.commit();
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			tx.rollback();
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}
}
