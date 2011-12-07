package br.com.lawbook.managedbean.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import org.junit.Test;

import br.com.lawbook.managedbean.AdminBean;
import br.com.lawbook.model.User;

public class AdminBeanTest {
	
	private static AdminBean adminBean = new AdminBean();
	private final static Logger LOG = Logger.getLogger("AdminBeanTest");

	@Test
	public void testValidateUser() {
		
		User newUser = adminBean.getChosenUser();

		//test new user without setting email
		try {
			adminBean.validateUser();
			fail("Should have thrown an exception");
		} catch (NoSuchAlgorithmException e) {
			fail("Wrong excepition thrown");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().equals("Email is required"));
			LOG.info("#### OK " + e.getMessage());
			newUser.setEmail("email");
		}
		

		//test new user without setting username
		try {
			adminBean.validateUser();
			fail("Should have thrown an exception");
		} catch (NoSuchAlgorithmException e) {
			fail("Wrong excepition thrown");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().equals("Username is required"));
			LOG.info("#### OK " + e.getMessage());
			newUser.setUserName("username");
		}
		
		
		//test new user without password and confirmation 
		try {
			adminBean.validateUser();
			fail("Should have thrown an exception");
		} catch (NoSuchAlgorithmException e) {
			fail("Wrong excepition thrown");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().equals("Password is required"));
			LOG.info("#### OK " + e.getMessage());
		}
		
		
		//test new user with password and confirmation different
		try {
			adminBean.setPassConfirmation("pass");
			adminBean.setPass("passDifferent");
			adminBean.validateUser();
			fail("Should have thrown an exception");
		} catch (NoSuchAlgorithmException e) {
			fail("Wrong excepition thrown");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().equals("Password and his confirmation doesn't match"));
			LOG.info("#### OK " + e.getMessage());
		}
		
		
		//test new user with password length less than 5
		try {
			adminBean.setPassConfirmation("pass");
			adminBean.setPass("pass");
			adminBean.validateUser();
			fail("Should have thrown an exception");
		} catch (NoSuchAlgorithmException e) {
			fail("Wrong excepition thrown");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().equals("Password must have at least 5 characters"));
			LOG.info("#### OK " + e.getMessage());
		}
		
		
		//test an existent user passing no password
		try {
			adminBean.setPassConfirmation("");
			adminBean.setPass("");
			newUser.setPassword("12345");
			adminBean.validateUser();
			LOG.info("#### OK ");
		} catch (NoSuchAlgorithmException e) {
			fail(e.getMessage());
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}
		
	}

	@Test
	public void testCompleteUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCustomerInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAuthorities() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDisabled() {
		fail("Not yet implemented");
	}

}
