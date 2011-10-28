package br.com.lawbook.dao.hibernate;

import org.hibernate.Session;

import br.com.lawbook.dao.LocationDAO;
import br.com.lawbook.model.Location;

/**
 * @author Edilson Luiz Ales Junior
 * @version 16SEP2011-01 
 * 
 */

public class HibernateLocationDAO extends HibernateGenericDAO<Location> implements LocationDAO {

	public HibernateLocationDAO(Session session) {
		super(session);
	}


}
