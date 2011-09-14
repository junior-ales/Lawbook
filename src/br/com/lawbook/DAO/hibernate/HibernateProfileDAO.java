package br.com.lawbook.DAO.hibernate;

import org.hibernate.Session;

import br.com.lawbook.DAO.ProfileDAO;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 13SEP2011-01 
 * 
 */

public class HibernateProfileDAO extends HibernateGenericDAO<Profile> implements ProfileDAO {

	public HibernateProfileDAO(Session session) {
		super(session);
	}
}
