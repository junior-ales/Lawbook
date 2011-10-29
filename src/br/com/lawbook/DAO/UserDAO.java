package br.com.lawbook.dao;

import java.io.Serializable;

import org.hibernate.HibernateException;

import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OCT2011-04
 * 
 */
public interface UserDAO {

	User create(User user) throws IllegalArgumentException, HibernateException;
	
	User update(User entity) throws HibernateException ;
	
	User checkIfExist(String email, String userName) throws HibernateException ;
	
	User getUserById(Serializable id) throws HibernateException ;

	User getUserByUserName(String userName) throws HibernateException;
}
