package br.com.lawbook.dao;

import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Event;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 06NOV2011-04
 * 
 */
public interface EventDAO {

	void create(Event event) throws IllegalArgumentException, HibernateException;

	void update(Event event) throws IllegalArgumentException, HibernateException;
	
	void delete(Event event) throws IllegalArgumentException, HibernateException;
	
	List<Event> getProfileEvents(Profile creator) throws HibernateException;

	Event getEventById(Long eventId) throws HibernateException;
}
