package br.com.lawbook.business.service;

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
 * @version 27NOV2011-08
 *
 */
public final class EventService implements Serializable {

	private final EventDAO dao;
	private static EventService instance;
	private static final long serialVersionUID = 5397054248713362518L;

	private EventService() {
		this.dao = new EventDAOImpl();
	}

	public static EventService getInstance() {
		if (instance == null) {
			instance = new EventService();
		}
		return instance;
	}

	public void create(final Event event) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(event, "EventService: create: event");
		this.dao.create(event);
	}

	public void update(final Event event) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(event, "EventService: update: event");
		this.dao.update(event);
	}

	public void delete(final Event event) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(event, "EventService: delete: event");
		this.dao.delete(event);
	}

	public List<Event> getProfileEvents(final Profile creator, final Date startDate, final Date endDate) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(creator, "EventService: getProfileEvents: creator");
		JavaUtil.validateParameter(startDate, "EventService: getProfileEvents: startDate");
		JavaUtil.validateParameter(endDate, "EventService: getProfileEvents: endDate");
		return this.dao.getProfileEvents(creator, startDate, endDate);
	}

	public List<Event> getUpcomingEvents(final Profile creator) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(creator, "EventService: getProfileEvents: creator");
		return this.dao.getUpcomingEvents(creator);
	}

	public Event getEventById(final Long eventId) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(eventId, "EventService: getEventById: eventId");
		return this.dao.getEventById(eventId);
	}

	public int getEventsCount() throws HibernateException {
		return Integer.parseInt(this.dao.getEventsCount().toString());
	}

}
