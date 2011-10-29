package br.com.lawbook.business;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.lawbook.dao.ProfileDAO;
import br.com.lawbook.dao.impl.ProfileDAOImpl;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OCT2011-08 
 */
public final class ProfileService implements Serializable {
	
	private static ProfileService instance;
	private static final long serialVersionUID = -7975898388328234855L;

	private ProfileService() {
	}
	
	public static ProfileService getInstance() {
		if (instance == null) {
			instance = new ProfileService();
		}
		return instance;
	}

	public Profile create(Profile profile) throws IllegalArgumentException, HibernateException  {
		JavaUtil.validateParameter(profile);
		ProfileDAO dao = new ProfileDAOImpl();
		try {
			Profile p = dao.create(profile);
			return p;
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new HibernateException(e);
		} 
	}
	
	public Profile getAuthorizedUserProfile() throws IllegalArgumentException, HibernateException, Exception {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) throw new Exception("SecurityContext is null");
        
		Authentication authentication = context.getAuthentication();
        if (authentication == null) throw new Exception("Authentication is null");
    	
        String username = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
        return this.getProfileByUserName(username);
	}

	public boolean checkIfExist(Long profileId) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(profileId);
		return this.getProfileById(profileId) == null ? false : true;
	}
	
	public Profile getProfileById(Long id) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(id);
		ProfileDAO dao = new ProfileDAOImpl();
		return dao.getProfileById(id);
	}

	public Profile getProfileByUserName(String userName) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(userName);
		ProfileDAO dao = new ProfileDAOImpl();
		return dao.getProfileByUserName(userName);
	}

	public Profile getProfileByUserId(Long userId) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(userId);
		ProfileDAO dao = new ProfileDAOImpl();
		return dao.getProfileByUserId(userId);
	}
	
}
