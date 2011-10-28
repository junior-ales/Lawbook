package br.com.lawbook.business;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.lawbook.dao.FactoryDAO;
import br.com.lawbook.dao.ProfileDAO;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OCT2011-08 
 */

public final class ProfileService implements Serializable {
	
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

	public Profile create(Profile profile) throws IllegalArgumentException {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		ProfileDAO dao = factory.getProfileDAO();
		try {
			factory.beginTx();
			Profile p = dao.create(profile);
			factory.shutTx();
			return p;
		} catch (HibernateException e) {
			factory.cancelTx();
			e.printStackTrace();
			throw new HibernateException(e);
		} 
	}
	
	public Profile getAuthorizedUserProfile() throws Exception {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) throw new Exception("SecurityContext is null");
        
		Authentication authentication = context.getAuthentication();
        if (authentication == null) throw new Exception("Authentication is null");
    	
        String username = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
        return this.getProfileByUserName(username);
	}

	public boolean checkIfExist(Long profileId) {
		return getProfileById(profileId) == null ? false : true;
	}
	
	public Profile getProfileById(Long id) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		ProfileDAO dao = factory.getProfileDAO();
		return dao.getById(id);
	}

	public Profile getProfileByUserName(String userName) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		ProfileDAO dao = factory.getProfileDAO();
		return dao.getProfileByUserName(userName);
	}

	public Profile getProfileByUserId(Long userId) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		ProfileDAO dao = factory.getProfileDAO();
		return dao.getProfileByUserId(userId);
	}
	
}
