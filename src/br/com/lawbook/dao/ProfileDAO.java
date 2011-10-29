package br.com.lawbook.dao;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OCT2011-06
 * 
 */
public interface ProfileDAO {

	Profile create(Profile profile) throws IllegalArgumentException, HibernateException;
	
	Profile update(Profile profile) throws HibernateException;
	
	Profile checkIfUserHasProfile(Long userId) throws HibernateException;
	
	Profile getProfileById(Long id) throws HibernateException;

	Profile getProfileByUserId(Long userId) throws HibernateException;

	Profile getProfileByUserName(String userName) throws HibernateException;

}
