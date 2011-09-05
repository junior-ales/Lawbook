package br.com.lawbook.model.test;

import java.util.Calendar;

import org.junit.Test;

import br.com.lawbook.model.Message;

/**
 * @author Edilson Luiz Ales Junior
 * @version 05SEP2011-01 
 * 
 */

public class MessageTest {

	@Test(expected=IllegalArgumentException.class)
	public void testSetDateTime() {
		Message message = new Message();
		Calendar now = Calendar.getInstance();
		
		// testing passing the next second as parameter
		now.add(Calendar.SECOND, 1);
		message.setDateTime(now);
	}

}
