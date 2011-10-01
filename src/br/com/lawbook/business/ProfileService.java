package br.com.lawbook.business;

import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.DAO.ProfileDAO;
import br.com.lawbook.model.Profile;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 01OCT2011-04 
 */

public class ProfileService {
	
	private static ProfileService instance;

	private ProfileService() {
	}
	
	public static ProfileService getInstance() {
		if (instance == null) {
			instance = new ProfileService();
		}
		return instance;
	}
	
	public Profile getProfileById(Long id) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		ProfileDAO dao = factory.getProfileDAO();
		return dao.getById(id);
	}

	public void save(Profile profile) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		ProfileDAO dao = factory.getProfileDAO();
		factory.beginTx();
		dao.save(profile);
		factory.shutTx();
	}

	public boolean checkIfExist(Long profileId) {
		return getProfileById(profileId) == null ? false : true;
	}

	public Profile getProfileBy(String userName) {
		UserService userService = UserService.getInstance();
		User user = userService.getUserBy(userName);
		return this.getProfileBy(user.getId());
	}

	private Profile getProfileBy(Long userId) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		ProfileDAO dao = factory.getProfileDAO();
		return dao.getProfileBy(userId);
	}
	
}
