package br.com.lawbook.dao;

import org.hibernate.HibernateException;

import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 30OCT2011-06
 * 
 */
public interface UserDAO {

	void create(User user) throws IllegalArgumentException, HibernateException;
	
	void update(User user) throws HibernateException ;
	
	boolean checkIfExist(User user) throws HibernateException ;
	
	User getUserById(Long id) throws HibernateException ;

	User getUserByUserName(String userName) throws HibernateException;
}
