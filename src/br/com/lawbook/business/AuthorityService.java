package br.com.lawbook.business;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.AuthorityDAO;
import br.com.lawbook.dao.FactoryDAO;
import br.com.lawbook.model.Authority;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OCT2011-04
 *  
 */
public final class AuthorityService {
	
	private static AuthorityService instance;

	private AuthorityService() {
	}
	
	public static AuthorityService getInstance() {
		if (instance == null) {
			instance = new AuthorityService();
		}
		return instance;
	}
	
	public Authority create(String authorityName) throws IllegalArgumentException {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		AuthorityDAO dao = factory.getAuthorityDAO();
		try {
			factory.beginTx();
			Authority a = dao.create(authorityName);
			factory.shutTx();
			return a;
		} catch (HibernateException e) {
			factory.cancelTx();
			e.printStackTrace();
			throw new HibernateException(e.getMessage());
		} 
	}

	public Authority getByName(String name) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		AuthorityDAO dao = factory.getAuthorityDAO();
		try {
			return dao.getByName(name);
		} catch (HibernateException e) {
			throw new HibernateException(e.getMessage());
		} 
	}
	
}
