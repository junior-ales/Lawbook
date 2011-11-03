package br.com.lawbook.dao;

import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Event;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 03NOV2011-02
 * 
 */
public interface EventDAO {

	void create(Event event) throws HibernateException;

	List<Event> getProfileEvents(Profile creator) throws HibernateException;

}
