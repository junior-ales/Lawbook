package br.com.lawbook.dao;

import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Authority;

/**
 * @author Edilson Luiz Ales Junior
 * @version 21NOV2011-04
 * 
 */
public interface AuthorityDAO {

	void create(Authority auth) throws HibernateException, IllegalArgumentException;
	
	Authority getByName(String authorityName) throws HibernateException;

	List<Authority> getAll() throws HibernateException;

}
