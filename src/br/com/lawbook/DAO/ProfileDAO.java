package br.com.lawbook.DAO;

import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 24OCT2011-04
 * 
 */
public interface ProfileDAO extends GenericDAO<Profile> {

	Profile create(Profile profile);

	Profile getProfileByUserName(String userName);

	Profile getProfileByUserId(Long userId);

	Profile checkIfUserHasProfile(Long userId);
}
