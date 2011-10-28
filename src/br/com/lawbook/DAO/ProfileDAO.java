package br.com.lawbook.dao;

import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OCT2011-05
 * 
 */
public interface ProfileDAO extends GenericDAO<Profile> {

	Profile create(Profile profile);

	Profile getProfileByUserName(String userName);

	Profile getProfileByUserId(Long userId);

	Profile checkIfUserHasProfile(Long userId);
}
