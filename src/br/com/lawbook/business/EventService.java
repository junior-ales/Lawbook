package br.com.lawbook.business;

import java.io.Serializable;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.EventDAO;
import br.com.lawbook.dao.impl.EventDAOImpl;
import br.com.lawbook.model.Event;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 02NOV2011-01
 * 
 */
public class EventService implements Serializable{

	private static EventService instance;
	private static final long serialVersionUID = 7880789552218790351L;

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

}
