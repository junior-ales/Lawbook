package br.com.lawbook.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.EventDAO;
import br.com.lawbook.model.Event;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 03NOV2011-02
 * 
 */
public class EventDAOImpl implements EventDAO {

	@Override
	public void create(Event event) throws HibernateException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(Event event) {
		// TODO Auto-generated method stub
		
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

}
