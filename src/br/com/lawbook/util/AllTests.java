package br.com.lawbook.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.lawbook.business.test.*;

/**
 * @author Edilson Luiz Ales Junior
 * @version 07NOV2011-05
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ AuthorityServiceTest.class, UserServiceTest.class,
		ProfileServiceTest.class, PostServiceTest.class, EventServiceTest.class})
public class AllTests {

}
