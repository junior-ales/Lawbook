package br.com.lawbook.business.service;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.LocationDAO;
import br.com.lawbook.dao.impl.LocationDAOImpl;
import br.com.lawbook.model.Location;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-01
 * 
 */
public final class LocationService {

	private static LocationService instance;
	
	private LocationService() {
	}
	
	public static LocationService getInstance() {
		if (instance == null) {
			instance = new LocationService();
		}
		return instance;
	}
	
	public void save(final Location location) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(location, "LocationService: save: location");
		final LocationDAO dao = new LocationDAOImpl();
		dao.save(location);
	}
	
	public void update(final Location location) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(location, "LocationService: update: location");
		final LocationDAO dao = new LocationDAOImpl();
		dao.update(location);
	}
	
	public void delete(final Location location) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(location, "LocationService: delete: location");
		final LocationDAO dao = new LocationDAOImpl();
		dao.delete(location);
	}
	
}
