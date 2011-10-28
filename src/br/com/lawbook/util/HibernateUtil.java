package br.com.lawbook.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Edilson Luiz Ales Junior
 * @version 04SEP2011-01 
 * 
 */

public class HibernateUtil {
	
	private static SessionFactory SESSION_FACTORY;
	private static Session session;

    static {
        try {
            SESSION_FACTORY = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
	public static SessionFactory getSessionFactory() {
    	if (SESSION_FACTORY == null) {
	    	try {
	    		SESSION_FACTORY = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	    	} catch (Throwable ex) {
		    	System.err.println("Initial SessionFactory creation failed." + ex);
		    	throw new ExceptionInInitializerError(ex);
	    	}
    	}
    	return SESSION_FACTORY;
	}

    public static Session getSession() {
    	if (session == null || !session.isOpen()) {
    		session = SESSION_FACTORY.openSession();
    	}
        return session;
    }
	
}
