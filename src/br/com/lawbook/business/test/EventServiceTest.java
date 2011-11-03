package br.com.lawbook.business.test;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.lawbook.business.EventService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Event;

/**
 * @author Edilson Luiz Ales Junior
 * @version 02NOV2011-01
 * 
 */
public class EventServiceTest {
	
	private final static Logger LOG = Logger.getLogger("ProfileServiceTest");

	@BeforeClass
	public static void create() {
		Event event = new Event();
		
		event.setCreator(ProfileService.getInstance().getProfileByUserName("admin"));
		event.setContent("Meeting about the documents");
		event.setAllDay(false);
		event.setStartDate(getDate("02/11/2011 23:47"));
		event.setEndDate(getDate("03/11/2011 08:00"));
		
		EventService.getInstance().create(event);
		assertNotNull(event.getId());
		LOG.info("Event created successfully");
	}
	
	@Test
	public void populateSchedule() {
		
	}
	
	private static Date getDate(String dateString) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			c.setTime(df.parse(dateString));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c.getTime();
	}
	
}
