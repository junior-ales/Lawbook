package br.com.lawbook.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.lawbook.dao.AuthorityDAO;
import br.com.lawbook.model.Authority;

/**
 * @author Edilson Luiz Ales Junior
 * @version 24OCT2011-03
 * 
 */
public class AuthorityDAOImpl extends GenericDAOImpl<Authority> implements AuthorityDAO {

	public AuthorityDAOImpl(Session session) {
		super(session);
	}
	
	@Override
	public Authority create(String authName) throws HibernateException {
		Authority auth = this.checkIfExist(authName);
		if (auth != null) {
			throw new IllegalArgumentException("Authority name " + authName + " already exist");
		}
		auth = new Authority();
		auth.setName(authName);
		return save(auth);
	}
	
	@Override
	public Authority checkIfExist(String name) {
		Criteria crit = this.getSession().createCriteria(Authority.class);
		crit.add(Restrictions.eq("name", name));
		return (Authority) crit.uniqueResult();
	}

	@Override
	public Authority getByName(String name) {
		return this.checkIfExist(name);
	}
	
}
