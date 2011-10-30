package br.com.lawbook.dao.impl;

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
 * @version 30OCT2011-07
 * 
 */
public class UserDAOImpl implements UserDAO {

	private final static Logger LOG = Logger.getLogger("UserDAOImpl");
	
	@Override
	public void create(User user) throws IllegalArgumentException, HibernateException {
		
		Session session = HibernateUtil.getSession();
		
		if (this.checkIfExist(user, session)) {
			session.close();
			LOG.info("Hibernate Session closed");
			String msg = "Username (" + user.getUserName() + ") or email ("+ user.getEmail() + ") already exist";
			throw new IllegalArgumentException(msg);
		}
			
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(user);
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
	public void update(User user) throws HibernateException {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.update(user);
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
	public boolean checkIfExist(User user) throws HibernateException {
		Session session = HibernateUtil.getSession();
		try {
			return checkIfExist(user, session);
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
		
	}
	
	@Override
	public User getUserById(Long id) throws HibernateException {
		Session session = HibernateUtil.getSession();
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
		User user = new User();
		user.setUserName(userName);
		if (checkIfExist(user)) return user;
		throw new IllegalArgumentException("Username " + userName + " doesn't exist");
	}
	
	/* In order to optimize the database connection usage
	 * this method reuse sessions, that's why it isn't closed here */
	private boolean checkIfExist(User user, Session session) throws HibernateException {
		User userAux = new User();
		Criteria crit;
		try {
			if (user.getEmail() != null && !user.getEmail().trim().equals("")) {
				if (user.getUserName() != null && !user.getUserName().trim().equals("")) {
					Query query = session.createQuery("from lwb_user u where u.userName = :userName or u.email = :email");
					query.setParameter("email", user.getEmail());
					query.setParameter("userName", user.getUserName());
					query.setMaxResults(1);
					userAux = (User) query.uniqueResult();
				} else {
					crit = session.createCriteria(User.class);
					crit.add(Restrictions.eq("email", user.getEmail()));
					userAux = (User) crit.uniqueResult();
				}
			} else {
				crit = session.createCriteria(User.class);
				crit.add(Restrictions.eq("userName", user.getUserName()));
				userAux = (User) crit.uniqueResult();
			}
			
			if (userAux == null) return false;
			userAux.copyTo(user);
			return true;

		} catch (Exception e) {
			session.close();
			LOG.info("Hibernate Session closed");
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		}
	}
	
}
