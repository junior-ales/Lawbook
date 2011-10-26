package br.com.lawbook.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.lawbook.business.test.AuthorityServiceTest;
import br.com.lawbook.business.test.PostServiceTest;
import br.com.lawbook.business.test.ProfileServiceTest;
import br.com.lawbook.business.test.UserServiceTest;

/**
 * @author Edilson Luiz Ales Junior
 * @version 26OUT2011-04
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ AuthorityServiceTest.class, UserServiceTest.class,
		ProfileServiceTest.class, PostServiceTest.class })
public class AllTests {

}
