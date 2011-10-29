package br.com.lawbook.dao;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Location;


/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-01 
 * 
 */
public interface LocationDAO {

	void save(Location location) throws HibernateException;

	void update(Location location) throws HibernateException;
	
	void delete(Location location) throws HibernateException;

}
