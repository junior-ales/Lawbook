package br.com.lawbook.business;

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
	
	public void save(Location location) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(location, "LocationService: save: location");
		LocationDAO dao = new LocationDAOImpl();
		dao.save(location);
	}
	
	public void update(Location location) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(location, "LocationService: update: location");
		LocationDAO dao = new LocationDAOImpl();
		dao.update(location);
	}
	
	public void delete(Location location) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(location, "LocationService: delete: location");
		LocationDAO dao = new LocationDAOImpl();
		dao.delete(location);
	}
	
}
