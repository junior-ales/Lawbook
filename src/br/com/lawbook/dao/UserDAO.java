package br.com.lawbook.dao;

import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 19NOV2011-07
 * 
 */
public interface UserDAO {

	void create(User user) throws IllegalArgumentException, HibernateException;
	
	void update(User user) throws HibernateException;
	
	List<User> getAll();
	
	boolean checkIfExist(User user) throws HibernateException;
	
	User getUserById(Long id) throws HibernateException;

	User getUserByUserName(String userName) throws HibernateException;
}
