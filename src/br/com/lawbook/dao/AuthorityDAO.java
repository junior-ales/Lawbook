package br.com.lawbook.dao;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Authority;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-03
 * 
 */
public interface AuthorityDAO {

	void create(Authority auth) throws HibernateException, IllegalArgumentException;
	
	Authority getByName(String authorityName) throws HibernateException;

}
