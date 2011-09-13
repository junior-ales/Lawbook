package br.com.lawbook.business;

import java.util.List;

import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.DAO.ProfileDAO;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 13SEP2011-02 
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
		return null;
	}

	public List<Post> getStream(Profile profile) {
		return null;
	}

	public void save(Profile p) {
		FactoryDAO factoryDAO = FactoryDAO.getFactoryDAO();
		ProfileDAO profileDAO = factoryDAO.getProfileDAO();
		factoryDAO.beginTx();
		profileDAO.save(p);
		factoryDAO.shutTx();
	}
	
}
