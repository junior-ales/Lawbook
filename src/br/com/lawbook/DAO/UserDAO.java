package br.com.lawbook.dao;

import org.hibernate.HibernateException;

import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 23OCT2011-03
 *  
 */
public interface UserDAO extends GenericDAO<User>{

	User checkIfExist(String email, String userName);

	User getByUserName(String userName);

	User create(User user) throws IllegalArgumentException, HibernateException;

}
