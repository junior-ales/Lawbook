package br.com.lawbook.dao.impl;

import org.hibernate.Session;

import br.com.lawbook.dao.LocationDAO;
import br.com.lawbook.model.Location;

/**
 * @author Edilson Luiz Ales Junior
 * @version 16SEP2011-01 
 * 
 */

public class LocationDAOImpl extends GenericDAOImpl<Location> implements LocationDAO {

	public LocationDAOImpl(Session session) {
		super(session);
	}


}
