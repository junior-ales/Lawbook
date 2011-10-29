package br.com.lawbook.dao.impl;

import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.lawbook.dao.AuthorityDAO;
import br.com.lawbook.model.Authority;
import br.com.lawbook.util.HibernateUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-04
 * 
 */
public class AuthorityDAOImpl implements AuthorityDAO {
	
	private final static Logger LOG = Logger.getLogger("AuthorityDAOImpl");
	
	@Override
	public Authority create(String authName) throws HibernateException, IllegalArgumentException {
		Authority auth = this.checkIfExist(authName);
		if (auth != null) {
			throw new IllegalArgumentException("Authority name " + authName + " already exist");
		}
		auth = new Authority();
		auth.setName(authName);
		return save(auth);
	}
	
	@Override
	public Authority checkIfExist(String authName) throws HibernateException {
		Session session = HibernateUtil.getSession();
		LOG.info("Hibernate Session opened");
		try {
			Criteria crit = session.createCriteria(Authority.class);
			crit.add(Restrictions.eq("name", authName));
			return (Authority) crit.uniqueResult();
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}

	@Override
	public Authority getByName(String authName) throws HibernateException {
		return this.checkIfExist(authName);
	}
	
	private Authority save(Authority auth) throws HibernateException {
		Session session = HibernateUtil.getSession();
		LOG.info("Hibernate Session opened");
		Transaction tx = session.beginTransaction();
		try {
			session.save(auth);
			tx.commit();
			return auth;
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
