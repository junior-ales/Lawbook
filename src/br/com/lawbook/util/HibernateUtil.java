package br.com.lawbook.util;

import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-02 
 * 
 */
public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	private static Session session;
	private final static Logger LOG = Logger.getLogger("HibernateUtil");

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (HibernateException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
	public static SessionFactory getSessionFactory() {
    	if (sessionFactory == null) {
	    	try {
	    		sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	    	} catch (HibernateException ex) {
		    	LOG.severe("Initial SessionFactory creation failed." + ex);
		    	throw new ExceptionInInitializerError(ex);
	    	}
    	}
    	return sessionFactory;
	}

    public static Session getSession() {
    	if (session == null || !session.isOpen()) {
    		session = sessionFactory.openSession();
    	}
        return session;
    }
	
}
