package br.com.lawbook.business;

import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.AuthorityDAO;
import br.com.lawbook.dao.impl.AuthorityDAOImpl;
import br.com.lawbook.model.Authority;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 21NOV2011-07
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
	
	public void create(Authority auth) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(auth, "AuthorityService: create: auth");
		AuthorityDAO dao = new AuthorityDAOImpl();
		dao.create(auth);
	}

	public Authority getByName(String authorityName) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(authorityName, "AuthorityService: getByName: authorityName");
		AuthorityDAO dao = new AuthorityDAOImpl();
		return dao.getByName(authorityName);
	}

	public List<Authority> getAll() throws HibernateException {
		AuthorityDAO dao = new AuthorityDAOImpl();
		return dao.getAll();
	}
	
}
