package br.com.lawbook.util;

import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-03 
 * 
 */
public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	private static Session session;
	private final static Logger LOG = Logger.getLogger("HibernateUtil");
    
	public static Session getSession() {
		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				try {
					sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
				} catch (HibernateException ex) {
					LOG.severe("Initial SessionFactory creation failed." + ex);
					throw new ExceptionInInitializerError(ex);
				}
			}
			session = sessionFactory.openSession();
			LOG.info("Hibernate Session opened");
		}
		return session;
	}
	
}
