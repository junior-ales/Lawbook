package br.com.lawbook.business;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.AuthorityDAO;
import br.com.lawbook.dao.impl.AuthorityDAOImpl;
import br.com.lawbook.model.Authority;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-05
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
	
	public Authority create(String authorityName) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(authorityName);
		AuthorityDAO dao = new AuthorityDAOImpl();
		return dao.create(authorityName);
	}

	public Authority getByName(String name) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(name);
		AuthorityDAO dao = new AuthorityDAOImpl();
		return dao.getByName(name);
	}
	
}
