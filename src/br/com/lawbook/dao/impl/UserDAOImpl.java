package br.com.lawbook.dao.impl;

import java.io.Serializable;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.lawbook.dao.UserDAO;
import br.com.lawbook.model.User;
import br.com.lawbook.util.HibernateUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OCT2011-05
 * 
 */
public class UserDAOImpl implements UserDAO {

	private final static Logger LOG = Logger.getLogger("HibernateUserDAO");
	
	@Override
	public User create(User user) throws IllegalArgumentException, HibernateException {
		User u = this.checkIfExist(user.getEmail(), user.getUserName());
		if (u != null) {
			String msg = "Username (" + user.getUserName() + ") or email ("+ user.getEmail() + ") already exist";
			throw new IllegalArgumentException(msg);
		}
		return save(user);
	}
	
	@Override
	public User update(User user) throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		LOG.info("Hibernate Session opened");
		Transaction tx = session.beginTransaction();
		try {
			session.update(user);
			tx.commit();
			return user;
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
	public User checkIfExist(String email, String userName) throws HibernateException {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		LOG.info("Hibernate Session opened");
		User u;
		Criteria crit;
		try {
			if (email != null && !email.trim().equals("")) {
				if (userName != null && !userName.trim().equals("")) {
					Query query = session.createQuery("from lwb_user u where u.userName = :userName or u.email = :email");
					query.setParameter("email", email);
					query.setParameter("userName", userName);
					query.setMaxResults(1);
					u = (User) query.uniqueResult();
				} else {
					crit = session.createCriteria(User.class);
					crit.add(Restrictions.eq("email", email));
					u = (User) crit.uniqueResult();
				}
			} else {
				crit = session.createCriteria(User.class);
				crit.add(Restrictions.eq("userName", userName));
				u = (User) crit.uniqueResult();
			}
			return u;
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
		
	}
	
	@Override
	public User getUserById(Serializable id) throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		LOG.info("Hibernate Session opened");
		try {
			return (User) session.get(User.class, id);
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}

	@Override
	public User getUserByUserName(String userName) throws HibernateException {
		return checkIfExist(null, userName);
	}
	
	private User save(User user) throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		LOG.info("Hibernate Session opened");
		Transaction tx = session.beginTransaction();
		try {
			session.save(user);
			tx.commit();
			return user;
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
