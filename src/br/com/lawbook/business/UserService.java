package br.com.lawbook.business;

import org.hibernate.HibernateException;

import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.DAO.UserDAO;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27OCT2011-06
 *  
 */
public class UserService  {

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
	
	private User create(User user) throws IllegalArgumentException {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		UserDAO dao = factory.getUserDAO();
		try {
			factory.beginTx();
			User u = dao.create(user);
			factory.shutTx();
			return u;
		} catch (HibernateException e) {
			factory.cancelTx();
			e.printStackTrace();
			throw new HibernateException(e.getMessage());
		} 
	}
	
	public User update(User user) throws HibernateException {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		UserDAO dao = factory.getUserDAO();
		factory.beginTx();
		User aux = dao.update(user);
		factory.shutTx();
		return aux;
	}
	
	public User getUserById(Long userId) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		UserDAO dao = factory.getUserDAO();
		return dao.getById(userId);
	}

	public User getUserByUserName(String userName) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		UserDAO dao = factory.getUserDAO();
		return dao.getByUserName(userName);
	}

}
