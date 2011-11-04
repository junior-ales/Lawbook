package br.com.lawbook.business.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.lawbook.business.EventService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Event;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 03NOV2011-02
 * 
 */
public class EventServiceTest {
	
	private final static Logger LOG = Logger.getLogger("ProfileServiceTest");

	@BeforeClass
	public static void createEvents() {
		Event event;
		for (int i = 0; i < 5; i++) {
			event = new Event();
			event.setCreator(ProfileService.getInstance().getProfileByUserName("admin"));
			event.setTitle(i + " - Meeting");
			event.setContent("Meeting about documents " + i);
			event.setAllDay(false);
			event.setStartDate(getDate(i + "/11/2011 09:00"));
			event.setEndDate(getDate((i+1) + "/11/2011 08:00"));
			create(event);
		}
	}
	
	@Test
	public void getEventById() {
		try {
			Event event = EventService.getInstance().getEventById(1L);
			assertNotNull(event.getEventId());
			LOG.info("Event was fetched successfully");
		} catch (IllegalArgumentException e) {
			LOG.severe("Error on attribute, please check parameter: " + e.getMessage());
			fail();
		} catch (HibernateException e) {
			LOG.severe("Error getting event from specified creator: " + e.getMessage());
			fail();
		}
	}
	
	@Test
	public void update() {
		String newEventTitle = "New Meeting - " + Calendar.getInstance().getTimeInMillis();
		Event event = EventService.getInstance().getEventById(1L);
		
		assertFalse(event.getTitle().equals(newEventTitle));
		
		try {
			event.setTitle("Updated Meeting");
			event.setAllDay(false);
			event.setStartDate(getDate("05/11/2011 23:00"));
			event.setEndDate(getDate("06/11/2011 08:00"));
			EventService.getInstance().update(event);
			
			LOG.info("s events were fetched successfully");
		} catch (IllegalArgumentException e) {
			LOG.severe("Error on attributes, please check parameters: " + e.getMessage());
			fail();
		} catch (HibernateException e) {
			LOG.severe("Error getting events from specified creator: " + e.getMessage());
			fail();
		}
	}
	
	@Test
	public void populateSchedule() {
		List<Event> newEvents = null;
		try {
			Profile creator = ProfileService.getInstance().getProfileByUserName("admin");
			newEvents = EventService.getInstance().getProfileEvents(creator);
			assertNotNull(newEvents);
			LOG.info(creator.getFirstName() + "'s events were fetched successfully");
		} catch (IllegalArgumentException e) {
			LOG.severe("Error on attributes, please check parameters: " + e.getMessage());
			fail();
		} catch (HibernateException e) {
			LOG.severe("Error getting events from specified creator: " + e.getMessage());
			fail();
		}
	}
	
	private static Date getDate(String dateString) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			c.setTime(df.parse(dateString));
		} catch (ParseException e) {
			e.printStackTrace();
			LOG.severe(e.getMessage());
			fail();
		}
		return c.getTime();
	}
	
	private static void create(Event event) {
		try {
			EventService.getInstance().create(event);
			assertNotNull(event.getId());
			LOG.info("Event created successfully");
		} catch (IllegalArgumentException e) {
			LOG.severe("Error on attributes, please check parameters: " + e.getMessage());
			fail();
		} catch (HibernateException e) {
			LOG.severe("Error saving events: " + e.getMessage());
			fail();
		}
	}
	
}
