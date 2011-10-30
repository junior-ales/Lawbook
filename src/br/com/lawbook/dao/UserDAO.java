package br.com.lawbook.dao;

import java.io.Serializable;

import org.hibernate.HibernateException;

import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 30OCT2011-05
 * 
 */
public interface UserDAO {

	void create(User user) throws IllegalArgumentException, HibernateException;
	
	void update(User user) throws HibernateException ;
	
	boolean checkIfExist(User user) throws HibernateException ;
	
	User getUserById(Serializable id) throws HibernateException ;

	User getUserByUserName(String userName) throws HibernateException;
}
