package br.com.lawbook.DAO;

import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 23OCT2011-03
 * 
 */
public interface ProfileDAO extends GenericDAO<Profile> {

	Profile getProfileByUser(Long userId);

	Profile create(Profile profile);
}
