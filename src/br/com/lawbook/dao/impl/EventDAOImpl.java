package br.com.lawbook.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.lawbook.dao.EventDAO;
import br.com.lawbook.model.Event;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 05NOV2011-03
 * 
 */
public class EventDAOImpl implements EventDAO {

	@Override
	public void create(Event event) throws HibernateException {
		/* TODO
		 *  
		 * Don't forget to verify before updating  if the event creator 
		 * has events in the same date and hour (exist method below)  
		 *
		 */
		
	}
	
	@Override
	public void update(Event event) throws HibernateException {
		/* TODO
		 *  
		 * Don't forget to verify before updating  if the event creator 
		 * has events in the same date and hour (exist method below)  
		 *
		 */
	}
	
	@Override
	public void delete(Event event) throws HibernateException {
		/* TODO
		 *  
		 * Don't forget to verify before deleting
		 * if the event exist (exist method below)  
		 *
		 */
	}

	@Override
	public List<Event> getProfileEvents(Profile creator) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event getEventById(Long eventId) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unused")
	private boolean exist(Event event, Session session) {
		// TODO
		return true;
	}

}
