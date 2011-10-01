package br.com.lawbook.DAO;

import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 01OCT2011-02
 * 
 */

public interface ProfileDAO extends GenericDAO<Profile> {

	Profile getProfileBy(Long userId);
}
