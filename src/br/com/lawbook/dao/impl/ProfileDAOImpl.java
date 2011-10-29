package br.com.lawbook.dao.impl;

import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.lawbook.dao.ProfileDAO;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.HibernateUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OCT2011-05 
 * 
 */
public class ProfileDAOImpl implements ProfileDAO {
	
	private final static Logger LOG = Logger.getLogger("ProfileDAOImpl");

	@Override
	public void create(Profile profile) throws IllegalArgumentException, HibernateException {
		Profile p = this.checkIfUserHasProfile(profile.getUserOwner().getId());
		if (p != null) {
			throw new IllegalArgumentException("User already has a profile");
		}
		save(profile);
	}
	
	@Override
	public void update(Profile profile) throws HibernateException {
		Session session = HibernateUtil.getSession();
		LOG.info("Hibernate Session opened");
		Transaction tx = session.beginTransaction();
		try {
			session.update(profile);
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
	public Profile checkIfUserHasProfile(Long userId) throws HibernateException{
		Session session = HibernateUtil.getSession();
		LOG.info("Hibernate Session opened");
		try {
			Query query = session.createQuery("from lwb_user_profile p where p.userOwner.id = :userId");
			query.setParameter("userId", userId);
			return (Profile) query.uniqueResult();
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}
	
	@Override
	public Profile getProfileById(Long id) {
		Session session = HibernateUtil.getSession();
		LOG.info("Hibernate Session opened");
		try {
			return (Profile) session.get(Profile.class, id);
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}
	
	@Override
	public Profile getProfileByUserId(Long userId) throws HibernateException {
		return this.checkIfUserHasProfile(userId);
	}

	@Override
	public Profile getProfileByUserName(String userName) throws HibernateException {
		Session session = HibernateUtil.getSession();
		LOG.info("Hibernate Session opened");
		try {
			Query query = session.createQuery("from lwb_user_profile p where p.userOwner.userName = :userName");
			query.setParameter("userName", userName);
			return (Profile) query.uniqueResult();
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}
	
	private void save(Profile profile) throws HibernateException{
		Session session = HibernateUtil.getSession();
		LOG.info("Hibernate Session opened");
		Transaction tx = session.beginTransaction();
		try {
			session.save(profile);
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
