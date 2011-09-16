package br.com.lawbook.business;

import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.DAO.LocationDAO;
import br.com.lawbook.model.Location;

/**
 * @author Edilson Luiz Ales Junior
 * @version 16SEP2011-01 
 * 
 */

public class LocationService {

	private static LocationService instance;
	
	private LocationService() {
	}
	
	public static LocationService getInstance() {
		if (instance == null) {
			instance = new LocationService();
		}
		return instance;
	}
	
	public void save(Location location) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		LocationDAO dao = factory.getLocationDAO();
		factory.beginTx();
		dao.save(location);
		factory.shutTx();
	}
	
}
