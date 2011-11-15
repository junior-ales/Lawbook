package br.com.lawbook.business;

import org.hibernate.HibernateException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.lawbook.dao.UserDAO;
import br.com.lawbook.dao.impl.UserDAOImpl;
import br.com.lawbook.model.User;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 15NOV2011-09
 *  
 */
public final class UserService  {

	private static UserService instance;

	private UserService() {
	}
	
	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}
	
	public void create(User user, String passConfirmation) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(user, "UserService: create(user, passConfirmation): user");
		
		if(!user.getPassword().equals(passConfirmation)) 
			throw new IllegalArgumentException("Password confirmation doesn't match");

		create(user);
	}
	
	private void create(User user) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(user, "UserService: create(user): user");
		UserDAO dao = new UserDAOImpl();
		dao.create(user);
	}
	
	public void update(User user) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(user, "UserService: update: user");
		UserDAO dao = new UserDAOImpl();
		dao.update(user);
	}
	
	public User getUserById(Long userId) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(userId, "UserService: getUserById: userId");
		UserDAO dao = new UserDAOImpl();
		return dao.getUserById(userId);
	}

	public User getUserByUserName(String userName) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(userName, "UserService: getUserByUserName: userName");
		UserDAO dao = new UserDAOImpl();
		return dao.getUserByUserName(userName);
	}
	
	public User getAuthorizedUser() throws IllegalArgumentException, HibernateException, Exception {
		
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) throw new Exception("SecurityContext is null");
        
		Authentication authentication = context.getAuthentication();
        if (authentication == null) throw new Exception("Authentication is null");
    	
        String username = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
        return this.getUserByUserName(username);
	}
}
