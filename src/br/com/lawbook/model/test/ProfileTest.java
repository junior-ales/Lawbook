package br.com.lawbook.model.test;

import java.util.Calendar;

import org.junit.Test;

import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 05SEP2011-01 
 * 
 */

public class ProfileTest {

	@Test(expected=IllegalArgumentException.class)
	public void testSetBirth() {

		Profile profile = new Profile();
		Calendar day = Calendar.getInstance();
		
		// testing passing the day of tomorrow as parameter
		day.add(Calendar.DAY_OF_MONTH, 1);
		profile.setBirth(day);

	}
}
