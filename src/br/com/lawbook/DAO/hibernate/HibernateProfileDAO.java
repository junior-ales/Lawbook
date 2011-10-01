package br.com.lawbook.DAO.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.lawbook.DAO.ProfileDAO;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 01OCT2011-02 
 * 
 */

public class HibernateProfileDAO extends HibernateGenericDAO<Profile> implements ProfileDAO {

	public HibernateProfileDAO(Session session) {
		super(session);
	}

	@Override
	public Profile getProfileBy(Long userId) {
		Query query = this.getSession().createQuery("from lwb_user_profile p where p.userOwner.id = :userId");
        query.setParameter("userId", userId);
        return (Profile) query.uniqueResult();
	}
}
