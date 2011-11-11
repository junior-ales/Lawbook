package br.com.lawbook.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Event;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 11NOV2011-06
 * 
 */
public interface EventDAO {

	void create(Event event) throws IllegalArgumentException, HibernateException;

	void update(Event event) throws IllegalArgumentException, HibernateException;
	
	void delete(Event event) throws IllegalArgumentException, HibernateException;
	
	List<Event> getProfileEvents(Profile creator, Date startDate, Date endDate) throws IllegalArgumentException, HibernateException;
	
	Event getEventById(Long eventId) throws HibernateException;

	Long getEventsCount() throws HibernateException;

}
