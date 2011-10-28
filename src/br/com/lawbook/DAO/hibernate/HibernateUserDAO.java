package br.com.lawbook.dao.hibernate;

import java.io.Serializable;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.lawbook.dao.UserDAO;
import br.com.lawbook.model.User;
import br.com.lawbook.util.HibernateUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OCT2011-05
 * 
 */

public class HibernateUserDAO implements UserDAO {

	private final static Logger LOG = Logger.getLogger("HibernateUserDAO");
	
	public User getUserById(Serializable id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		LOG.info("Hibernate Session (connection) opened");
		try {
			return (User) session.get(User.class, id);
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException("Problem saving new user: " + e.getMessage());
		} finally {
			session.close();
			LOG.info("Hibernate Session (connection) closed");
		}
	}

	public User save(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		LOG.info("Hibernate Session (connection) opened");
		Transaction tx = session.beginTransaction();
		try {
			session.save(user);
			tx.commit();
			return user;
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			tx.rollback();
			throw new HibernateException("Problem saving new user: " + e.getMessage());
		} finally {
			session.close();
			LOG.info("Hibernate Session (connection) closed");
		}
	}

	public User update(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		LOG.info("Hibernate Session (connection) opened");
		Transaction tx = session.beginTransaction();
		try {
			session.update(user);
			tx.commit();
			return user;
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			tx.rollback();
			throw new HibernateException("Problem updating user: " + e.getMessage());
		} finally {
			session.close();
			LOG.info("Hibernate Session (connection) closed");
		}
	}

	public User create(User user) throws HibernateException {
		User u = this.checkIfExist(user.getEmail(), user.getUserName());
		if (u != null) {
			String msg = "Username (" + user.getUserName() + ") or email ("+ user.getEmail() + ") already exist";
			throw new IllegalArgumentException(msg);
		}
		return save(user);
	}

	public User checkIfExist(String email, String userName) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		LOG.info("Hibernate Session (connection) opened");
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery("from lwb_user u where u.userName = :userName or u.email = :email");
			query.setParameter("email", email);
			query.setParameter("userName", userName);
			User u = (User) query.uniqueResult();
			tx.commit();
			return u;
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			tx.rollback();
			throw new HibernateException("Problem updating user: " + e.getMessage());
		} finally {
			session.close();
			LOG.info("Hibernate Session (connection) closed");
		}
		
	}

	public User getUserByUserName(String userName) {
		return checkIfExist("", userName);
	}

}
