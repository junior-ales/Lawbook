package br.com.lawbook.business.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.lawbook.business.service.EventService;
import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Event;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 11NOV2011-06
 * 
 */
public class EventServiceTest {
	
	private final static Logger LOG = Logger.getLogger("EventServiceTest");
	
	@BeforeClass
	public static void createEvents() {
		Event event;
		for (int i = 0; i < 6; i++) {
			Calendar c = Calendar.getInstance();
			event = new Event();
			event.setCreator(ProfileService.getInstance().getProfileByUserName("admin"));
			event.setTitle(c.getTimeInMillis() + " - Meeting");
			event.setContent(i + " - Meeting about documents");
			event.setAllDay(false);
			if (i != 0) {
				if (i % 2 == 0) {
					c.add(Calendar.DAY_OF_MONTH, -i);
				} else {
					c.add(Calendar.DAY_OF_MONTH, i);
				}
				event.setStartDate(c.getTime());
			} else {
				event.setStartDate(c.getTime());
			}
			c.add(Calendar.HOUR_OF_DAY, 2);
			event.setEndDate(c.getTime());
			
			create(event);
		}
	}
	
	@Test
	public void update() {
		String newEventTitle = "New Meeting - " + Calendar.getInstance().getTimeInMillis();
		try {
			EventService eventService = EventService.getInstance();
			Profile creator = ProfileService.getInstance().getProfileByUserName("admin");
			Long eventId = eventService.getUpcomingEvents(creator).get(0).getEventId();
			Event event = eventService.getEventById(eventId);
			
			assertFalse(event.getTitle().equals(newEventTitle));
			LOG.info("\nEvent " + event.getEventId() + ": " + event.getTitle() + 
					" Start Date: " + event.getStartDate() + 
					" End Date: " + event.getEndDate() + "\n");
			
			event.setTitle(newEventTitle);
			event.setStartDate(addDays(event));
			event.setEndDate(addDays(event));
			eventService.update(event);
			
			event = null;
			event = eventService.getEventById(eventId);
			assertTrue(event.getTitle().equals(newEventTitle));
			LOG.info("\nEvent " + event.getEventId() + ": " + event.getTitle() + 
					" Start Date: " + event.getStartDate() + 
					" End Date: " + event.getEndDate() + "\n");
			
			LOG.info("Events was successfully updated");
		} catch (IllegalArgumentException e) {
			LOG.warning("Error on attributes, please check parameters: " + e.getMessage());
			fail();
		} catch (HibernateException e) {
			LOG.severe("Error updating event: " + e.getMessage());
			fail();
		}
	}
	
	@Test
	public void delete() {
		try {
			EventService eventService = EventService.getInstance();
			Profile creator = ProfileService.getInstance().getProfileByUserName("admin");
			int count = eventService.getEventsCount();
			Event event = eventService.getUpcomingEvents(creator).get(0);
			Long eventId = event.getEventId();
			
			eventService.delete(event);
			assertNull(eventService.getEventById(eventId));
			assertTrue(eventService.getEventsCount() < count);
			LOG.info("Event was successfully deleted");
		} catch (IllegalArgumentException e) {
			LOG.warning("Error on attribute, please check parameter: " + e.getMessage());
			fail();
		} catch (HibernateException e) {
			LOG.severe("Error getting event by event id: " + e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getEventById() {
		try {
			Profile creator = ProfileService.getInstance().getProfileByUserName("admin");
			Long eventId = EventService.getInstance().getUpcomingEvents(creator).get(0).getEventId();
			Event event = EventService.getInstance().getEventById(eventId);
			assertNotNull(event.getEventId());
			LOG.info("Event was fetched successfully");
		} catch (IllegalArgumentException e) {
			LOG.warning("Error on attribute, please check parameter: " + e.getMessage());
			fail();
		} catch (HibernateException e) {
			LOG.severe("Error getting event by event id: " + e.getMessage());
			fail();
		}
	}
	
	@Test
	public void populateSchedule() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, 1);
		Date start = now.getTime();
		now.set(Calendar.DAY_OF_MONTH, 28);
		Date end = now.getTime();
		List<Event> newEvents = null;
		try {
			Profile creator = ProfileService.getInstance().getProfileByUserName("admin");
			newEvents = EventService.getInstance().getProfileEvents(creator, start, end);
			assertNotNull(newEvents);
			LOG.info(creator.getFirstName() + "'s events were fetched successfully");
		} catch (IllegalArgumentException e) {
			LOG.warning("Error on attributes, please check parameters: " + e.getMessage());
			fail();
		} catch (HibernateException e) {
			LOG.severe("Error getting events from specified creator: " + e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getUpcomingEvents() {
		List<Event> upcomingEvents = null;
		Date now = Calendar.getInstance().getTime();
		try {
			Profile creator = ProfileService.getInstance().getProfileByUserName("admin");
			upcomingEvents = EventService.getInstance().getUpcomingEvents(creator);
			
			for (Event event : upcomingEvents) {
				assertTrue(event.getStartDate().after(now));
			}
			
			LOG.info(creator.getFirstName() + "'s upcoming events were fetched successfully");
		} catch (IllegalArgumentException e) {
			LOG.warning("Error on attributes, please check parameters: " + e.getMessage());
			fail();
		} catch (HibernateException e) {
			LOG.severe("Error getting events from specified creator: " + e.getMessage());
			fail();
		}
	}
	
	private static void create(Event event) {
		try {
			EventService.getInstance().create(event);
			assertNotNull(event.getEventId());
			LOG.info("Event created successfully");
		} catch (IllegalArgumentException e) {
			LOG.warning("Error on attributes, please check parameters\n" + e);
			fail();
		} catch (HibernateException e) {
			LOG.severe("Error saving events\n" + e);
			fail();
		}
	}
	
	private Date addDays(Event event) {
		Calendar c = Calendar.getInstance();
		c.setTime(event.getStartDate());
		c.add(Calendar.DAY_OF_MONTH, 2);
		c.add(Calendar.HOUR_OF_DAY, 2);
		return c.getTime();
	}
	
}
