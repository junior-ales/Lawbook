package br.com.lawbook.business;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.EventDAO;
import br.com.lawbook.dao.impl.EventDAOImpl;
import br.com.lawbook.model.Event;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 11NOV2011-07
 * 
 */
public final class EventService implements Serializable {

	private static EventService instance;
	private static final long serialVersionUID = -7707732860167752283L;

	private EventService() {
	}
	
	public static EventService getInstance() {
		if (instance == null) {
			instance = new EventService();
		}
		return instance;
	}

	public void create(Event event) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(event, "EventService: create: event");
		EventDAO dao = new EventDAOImpl();
		dao.create(event);
	}
	
	public void update(Event event) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(event, "EventService: update: event");
		EventDAO dao = new EventDAOImpl();
		dao.update(event);
	}
	
	public void delete(Event event) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(event, "EventService: delete: event");
		EventDAO dao = new EventDAOImpl();
		dao.delete(event);
	}

	public List<Event> getProfileEvents(Profile creator, Date startDate, Date endDate) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(creator, "EventService: getProfileEvents: creator");
		JavaUtil.validateParameter(startDate, "EventService: getProfileEvents: startDate");
		JavaUtil.validateParameter(endDate, "EventService: getProfileEvents: endDate");
		EventDAO dao = new EventDAOImpl();
		return dao.getProfileEvents(creator, startDate, endDate);
	}
	
	public List<Event> getUpcomingEvents(Profile creator) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(creator, "EventService: getProfileEvents: creator");
		EventDAO dao = new EventDAOImpl();
		return dao.getUpcomingEvents(creator);
	}

	public Event getEventById(Long eventId) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(eventId, "EventService: getEventById: eventId");
		EventDAO dao = new EventDAOImpl();
		return dao.getEventById(eventId);
	}

	public int getEventsCount() throws HibernateException {
		EventDAO dao = new EventDAOImpl();
		return Integer.parseInt(dao.getEventsCount().toString());
	}

}
