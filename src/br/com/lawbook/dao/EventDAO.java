package br.com.lawbook.dao;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Event;

/**
 * @author Edilson Luiz Ales Junior
 * @version 02NOV2011-01
 * 
 */
public interface EventDAO {

	void create(Event event) throws IllegalArgumentException, HibernateException;

}
