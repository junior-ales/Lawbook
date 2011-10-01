package br.com.lawbook.DAO.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.lawbook.DAO.UserDAO;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 01OCT2011-02
 *  
 */

public class HibernateUserDAO extends HibernateGenericDAO<User> implements UserDAO {

	public HibernateUserDAO(Session session) {
		super(session);
	}

	@Override
	public boolean checkIfExist(String email, String userName) {
		Query query = this.getSession().createQuery("from lwb_user u where u.userName = :userName or u.email = :email");
		query.setParameter("email", email);
		query.setParameter("userName", userName);
		
		return (User) query.uniqueResult() == null ? false : true;
	}

	@Override
	public User getByUserName(String userName) {
		Query query = this.getSession().createQuery("from lwb_user u where u.userName = :userName");
        query.setParameter("userName", userName);
        return (User) query.uniqueResult();
	}

}
