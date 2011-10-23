package br.com.lawbook.DAO.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.lawbook.DAO.AuthorityDAO;
import br.com.lawbook.model.Authority;

/**
 * @author Edilson Luiz Ales Junior
 * @version 23OCT2011-02
 * 
 */
public class HibernateAuthorityDAO extends HibernateGenericDAO<Authority> implements AuthorityDAO {

	public HibernateAuthorityDAO(Session session) {
		super(session);
	}
	
	@Override
	public Authority create(String authName) throws HibernateException {
		Authority auth = this.checkIfExist(authName);
		if (auth != null) {
			throw new IllegalArgumentException("Authority name already exist");
		}
		auth = new Authority();
		auth.setName(authName);
		return save(auth);
	}
	
	@Override
	public Authority checkIfExist(String name) {
		Query query = this.getSession().createQuery("from lwb_authority a where a.name = :name");
		query.setParameter("name", name);
		return (Authority) query.uniqueResult();
	}

	@Override
	public Authority getByName(String name) {
		return this.checkIfExist(name);
	}
	
}
