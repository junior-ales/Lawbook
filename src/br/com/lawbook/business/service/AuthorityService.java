package br.com.lawbook.business.service;

import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.AuthorityDAO;
import br.com.lawbook.dao.impl.AuthorityDAOImpl;
import br.com.lawbook.model.Authority;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27NOV2011-08
 *
 */
public final class AuthorityService {

	private final AuthorityDAO dao;
	private static AuthorityService instance;

	private AuthorityService() {
		this.dao = new AuthorityDAOImpl();
	}

	public static AuthorityService getInstance() {
		if (instance == null) {
			instance = new AuthorityService();
		}
		return instance;
	}

	public void create(final Authority auth) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(auth, "AuthorityService: create: auth");
		this.dao.create(auth);
	}

	public Authority getByName(final String authorityName) throws HibernateException, IllegalArgumentException {
		JavaUtil.validateParameter(authorityName, "AuthorityService: getByName: authorityName");
		return this.dao.getByName(authorityName);
	}

	public List<Authority> getAll() throws HibernateException {
		return this.dao.getAll();
	}

}
