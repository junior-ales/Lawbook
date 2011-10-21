package br.com.lawbook.business;

import org.hibernate.HibernateException;

import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.DAO.UserDAO;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 20OCT2011-03
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
	
	public void create(String userName, String password, String email) {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setEmail(email);
		create(user);
	}
	
	public void create(User user) {
		this.save(user);
	}
	
	public User getUserById(Long userId) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		UserDAO dao = factory.getUserDAO();
		return dao.getById(userId);
	}

	private void save(User user) throws IllegalArgumentException, HibernateException {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		UserDAO dao = factory.getUserDAO();
		if (dao.checkIfExist(user.getEmail(), user.getUserName())) {
			throw new IllegalArgumentException("Username or email already exist");
		}
		factory.beginTx();
		dao.save(user);
		factory.shutTx();
	}
	
	public User update(User user) throws HibernateException {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		UserDAO dao = factory.getUserDAO();
		factory.beginTx();
		User aux = dao.update(user);
		factory.shutTx();
		return aux;
	}

	public User getUserBy(String userName) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		UserDAO dao = factory.getUserDAO();
		return dao.getByUserName(userName);
	}

}
