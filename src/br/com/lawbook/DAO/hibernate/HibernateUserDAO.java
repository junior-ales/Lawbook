package br.com.lawbook.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.lawbook.dao.UserDAO;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 24OCT2011-04
 *  
 */

public class HibernateUserDAO extends HibernateGenericDAO<User> implements UserDAO {

	public HibernateUserDAO(Session session) {
		super(session);
	}
	
	@Override
	public User create(User user) throws HibernateException {
		User u = this.checkIfExist(user.getEmail(), user.getUserName());
		if (u != null) {
			String msg = "Username (" + user.getUserName() + ") or email (" + user.getEmail() + ") already exist";
			throw new IllegalArgumentException(msg);
		}
		return save(user);
	}

	@Override
	public User checkIfExist(String email, String userName) {
		Query query = this.getSession().createQuery("from lwb_user u where u.userName = :userName or u.email = :email");
		query.setParameter("email", email);
		query.setParameter("userName", userName);
		return (User) query.uniqueResult();
	}

	@Override
	public User getByUserName(String userName) {
        return checkIfExist(userName, userName);
	}


}
