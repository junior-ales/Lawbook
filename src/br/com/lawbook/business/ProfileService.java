package br.com.lawbook.business;

import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.DAO.ProfileDAO;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 15SEP2011-03 
 */

public class ProfileService {
	
	private static ProfileService instance;

	private ProfileService() {
	}
	
	public static ProfileService getInstance() {
		if (instance == null) {
			instance = new ProfileService();
		}
		return instance;
	}
	
	public Profile getProfileById(Long id) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		ProfileDAO dao = factory.getProfileDAO();
		factory.beginTx();
		Profile profile = dao.getById(id);
		return profile;
	}

	public void save(Profile profile) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		ProfileDAO dao = factory.getProfileDAO();
		factory.beginTx();
		dao.save(profile);
		factory.shutTx();
	}

	public boolean checkIfExist(Long profileId) {
		return getProfileById(profileId) == null ? false : true;
	}
	
}
