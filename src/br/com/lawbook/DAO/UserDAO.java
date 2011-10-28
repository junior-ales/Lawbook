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

	User getUserById(Serializable id);

	User save(User entity);

	User update(User entity);

	User checkIfExist(String email, String userName);

	User getUserByUserName(String userName);

	User create(User user) throws IllegalArgumentException, HibernateException;

}
