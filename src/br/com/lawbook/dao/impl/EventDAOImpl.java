package br.com.lawbook.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.lawbook.dao.EventDAO;
import br.com.lawbook.model.Event;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.HibernateUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 12NOV2011-08
 * 
 */
public class EventDAOImpl implements EventDAO {

	private final static Logger LOG = Logger.getLogger("EventDAOImpl");
	
	@Override
	public void create(Event event) throws IllegalArgumentException, HibernateException {
		Session session = HibernateUtil.getSession();
		
		Transaction tx = session.beginTransaction();
		try {
			session.save(event);
			tx.commit();
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			tx.rollback();
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
		
	}
	
	@Override
	public void update(Event event) throws IllegalArgumentException, HibernateException {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.merge(event);
			tx.commit();
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			tx.rollback();
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}
	
	@Override
	public void delete(Event event) throws IllegalArgumentException, HibernateException {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.delete(event);
			tx.commit();
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			tx.rollback();
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Event> getProfileEvents(Profile creator, Date startDate, Date endDate) throws HibernateException {
		Session session = HibernateUtil.getSession();
		try {
			Criteria crit = session.createCriteria(Event.class);
			crit.add(Restrictions.eq("creator", creator));
			crit.add(Restrictions.between("startDate", startDate, endDate));
			return (List<Event>) crit.list();
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Event> getUpcomingEvents(Profile creator) {
		Session session = HibernateUtil.getSession();
		try {
			Criteria crit = session.createCriteria(Event.class);
			crit.add(Restrictions.eq("creator", creator));
			crit.add(Restrictions.ge("startDate", Calendar.getInstance().getTime()));
			crit.addOrder(Order.asc("startDate"));
			crit.setMaxResults(6);
			return (List<Event>) crit.list();
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}

	@Override
	public Event getEventById(Long eventId) throws HibernateException {
		Session session = HibernateUtil.getSession();
		try {
			Criteria crit = session.createCriteria(Event.class);
			crit.add(Restrictions.eq("id", eventId));
			return (Event) crit.uniqueResult();
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}
	
	@Override
	public Long getEventsCount() throws HibernateException {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {
			Criteria crit = session.createCriteria(Event.class);
			crit.setProjection(Projections.rowCount());
			Long count = (Long) crit.uniqueResult();
			tx.commit();
			return count;
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			tx.rollback();
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
	}

}
