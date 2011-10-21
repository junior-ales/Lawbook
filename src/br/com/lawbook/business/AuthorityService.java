package br.com.lawbook.business;

import java.util.List;

import br.com.lawbook.DAO.AuthorityDAO;
import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.model.Authority;

/**
 * @author Edilson Luiz Ales Junior
 * @version 20OCT2011-01
 *  
 */
public class AuthorityService {
	
	private static AuthorityService instance;

	private AuthorityService() {
	}
	
	public static AuthorityService getInstance() {
		if (instance == null) {
			instance = new AuthorityService();
		}
		return instance;
	}
	
	public void save(Authority authority) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		AuthorityDAO dao = factory.getAuthorityDAO();
		for (Authority a : this.getAll()) {
			if (authority.getName().equals(a.getName())) {
				throw new IllegalArgumentException("Authority name " + authority.getName() + " already exist");
			}
		} 
		factory.beginTx();
		dao.save(authority);
		factory.shutTx();
	}

	private List<Authority> getAll() {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		AuthorityDAO dao = factory.getAuthorityDAO();
		return dao.getAll();
	}
	
}
