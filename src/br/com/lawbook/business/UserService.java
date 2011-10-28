package br.com.lawbook.business;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.UserDAO;
import br.com.lawbook.dao.hibernate.HibernateUserDAO;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OCT2011-07
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
		if(!user.getPassword().equals(passConfirmation)) 
			throw new IllegalArgumentException("Password confirmation doesn't match");

		return create(user);
	}
	
	private User create(User user) throws IllegalArgumentException, HibernateException {
		UserDAO dao = new HibernateUserDAO();
		return dao.create(user);
	}
	
	public User update(User user) throws HibernateException {
		UserDAO dao = new HibernateUserDAO();
		return dao.update(user);
	}
	
	public User getUserById(Long userId) {
		UserDAO dao = new HibernateUserDAO();
		return dao.getUserById(userId);
	}

	public User getUserByUserName(String userName) {
		UserDAO dao = new HibernateUserDAO();
		return dao.getUserByUserName(userName);
	}

}
