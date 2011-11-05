package br.com.lawbook.dao;

import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Event;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 05NOV2011-03
 * 
 */
public interface EventDAO {

	void create(Event event) throws HibernateException;

	void update(Event event) throws HibernateException;
	
	void delete(Event event) throws HibernateException;
	
	List<Event> getProfileEvents(Profile creator) throws HibernateException;

	Event getEventById(Long eventId) throws HibernateException;
}
