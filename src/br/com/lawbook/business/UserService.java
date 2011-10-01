package br.com.lawbook.business;

import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.DAO.UserDAO;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 01OCT2011-02
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

	public boolean userExist(String email, String userName) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		UserDAO dao = factory.getUserDAO();
		return dao.checkIfExist(email, userName);
	}

	public boolean createUser(String userName, String password, String email) {
		if (userExist(email, userName)) return false;
		
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setEmail(email);
		this.save(user);
		
		return true;
	}
	
	public User getUserById(Long userId) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		UserDAO dao = factory.getUserDAO();
		return dao.getById(userId);
	}

	private void save(User user) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		UserDAO dao = factory.getUserDAO();
		factory.beginTx();
		dao.save(user);
		factory.shutTx();
	}

	public User getUserBy(String userName) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		UserDAO dao = factory.getUserDAO();
		return dao.getByUserName(userName);
	}
}
