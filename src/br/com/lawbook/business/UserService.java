package br.com.lawbook.business;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.UserDAO;
import br.com.lawbook.dao.impl.UserDAOImpl;
import br.com.lawbook.model.User;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-08
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
	
	public User create(User user, String passConfirmation) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(user, "UserService: create(user, passConfirmation): user");
		
		if(!user.getPassword().equals(passConfirmation)) 
			throw new IllegalArgumentException("Password confirmation doesn't match");

		return create(user);
	}
	
	private User create(User user) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(user, "UserService: create(user): user");
		UserDAO dao = new UserDAOImpl();
		return dao.create(user);
	}
	
	public User update(User user) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(user, "UserService: update: user");
		UserDAO dao = new UserDAOImpl();
		return dao.update(user);
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
}
