package br.com.lawbook.DAO.hibernate;

import org.hibernate.Session;

import br.com.lawbook.DAO.UserDAO;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 15SEP2011-01
 *  
 */

public class HibernateUserDAO extends HibernateGenericDAO<User> implements UserDAO {

	public HibernateUserDAO(Session session) {
		super(session);
	}

	@Override
	public boolean checkIfExist(String email, String userName) {
		// TODO check if exist one email or userName in the records
		return false;
	}

}
