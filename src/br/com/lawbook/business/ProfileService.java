package br.com.lawbook.business;

import java.io.Serializable;

import org.hibernate.HibernateException;

import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.DAO.ProfileDAO;
import br.com.lawbook.model.Profile;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 23OCT2011-06 
 */

public class ProfileService implements Serializable {
	
	private static final long serialVersionUID = -7975898388328234855L;
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

	public Profile create(Profile profile) throws IllegalArgumentException {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		ProfileDAO dao = factory.getProfileDAO();
		try {
			factory.beginTx();
			Profile p = dao.create(profile);
			factory.shutTx();
			return p;
		} catch (Exception e) {
			factory.cancelTx();
			throw new HibernateException(e.getMessage());
		} 
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
		return dao.getProfileByUser(userId);
	}
	
}
