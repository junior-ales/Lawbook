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
 * @version 09NOV2011-11 
 */
public final class ProfileService implements Serializable {
	
	private static ProfileService instance;
	private static Profile publicProfile;
	private Profile authProfile;
	private static final long serialVersionUID = 4353103189862156400L;

	private ProfileService() {
	}
	
	public static ProfileService getInstance() {
		if (instance == null) {
			instance = new ProfileService();
		}
		return instance;
	}

	public void create(Profile profile) throws IllegalArgumentException, HibernateException  {
		JavaUtil.validateParameter(profile, "ProfileService: create: profile");
		ProfileDAO dao = new ProfileDAOImpl();
		try {
			dao.create(profile);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new HibernateException(e);
		} 
	}
	
	public void update(Profile profile) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(profile, "ProfileService: update: profile");
		ProfileDAO dao = new ProfileDAOImpl();
		dao.update(profile);
	}
	
	public Profile getAuthorizedUserProfile() throws IllegalArgumentException, HibernateException, Exception {
		
		if(this.authProfile != null) return this.authProfile;
		
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) throw new Exception("SecurityContext is null");
        
		Authentication authentication = context.getAuthentication();
        if (authentication == null) throw new Exception("Authentication is null");
    	
        String username = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
        this.authProfile = this.getProfileByUserName(username);
        return this.authProfile;
	}

	public boolean checkIfExist(Long profileId) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(profileId, "ProfileService: checkIfExist: profileId");
		return this.getProfileById(profileId) == null ? false : true;
	}
	
	public Profile getProfileById(Long id) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(id, "ProfileService: getProfileById: id");
		ProfileDAO dao = new ProfileDAOImpl();
		return dao.getProfileById(id);
	}

	public Profile getProfileByUserName(String userName) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(userName, "ProfileService: getProfileByUserName: userName");
		ProfileDAO dao = new ProfileDAOImpl();
		return dao.getProfileByUserName(userName);
	}

	public Profile getProfileByUserId(Long userId) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(userId, "ProfileService: getProfileByUserId: userId");
		ProfileDAO dao = new ProfileDAOImpl();
		return dao.getProfileByUserId(userId);
	}
	
	public Profile getPublicProfile() {
		if (publicProfile == null) {
			try {
				publicProfile = this.getProfileByUserName("public");
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
		return publicProfile;
	}
	
}
