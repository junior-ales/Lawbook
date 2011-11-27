package br.com.lawbook.dao;

import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 26NOV2011-07
 * 
 */
public interface ProfileDAO {

	void create(Profile profile) throws IllegalArgumentException, HibernateException;
	
	void update(Profile profile) throws HibernateException;
	
	Profile checkIfUserHasProfile(Long userId) throws HibernateException;
	
	Profile getProfileById(Long id) throws HibernateException;

	Profile getProfileByUserId(Long userId) throws HibernateException;

	Profile getProfileByUserName(String userName) throws HibernateException;

	List<Profile> getAll() throws HibernateException;

}
