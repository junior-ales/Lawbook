package br.com.lawbook.DAO.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.lawbook.DAO.ProfileDAO;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 23OCT2011-03 
 * 
 */
public class HibernateProfileDAO extends HibernateGenericDAO<Profile> implements ProfileDAO {

	public HibernateProfileDAO(Session session) {
		super(session);
	}

	@Override
	public Profile getProfileByUser(Long userId) {
		Query query = this.getSession().createQuery("from lwb_user_profile p where p.userOwner.id = :userId");
        query.setParameter("userId", userId);
        return (Profile) query.uniqueResult();
	}

	@Override
	public Profile create(Profile profile) {
		// TODO Auto-generated method stub
		return null;
	}
}
