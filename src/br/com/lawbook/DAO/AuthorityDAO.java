package br.com.lawbook.DAO;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Authority;

/**
 * @author Edilson Luiz Ales Junior
 * @version 23OCT2011-02
 * 
 */
public interface AuthorityDAO extends GenericDAO<Authority> {

	Authority create(String authorityName) throws HibernateException ;
	
	Authority checkIfExist(String name);

	Authority getByName(String name);

}
