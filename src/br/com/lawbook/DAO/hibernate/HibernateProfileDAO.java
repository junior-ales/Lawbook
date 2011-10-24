package br.com.lawbook.DAO.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.lawbook.DAO.ProfileDAO;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 24OCT2011-04 
 * 
 */
public class HibernateProfileDAO extends HibernateGenericDAO<Profile> implements ProfileDAO {

	public HibernateProfileDAO(Session session) {
		super(session);
	}

	@Override
	public Profile create(Profile profile) {
		Profile p = this.checkIfUserHasProfile(profile.getUserOwner().getId());
		if (p != null) {
			throw new IllegalArgumentException("User already has a profile");
		}
		return save(profile);
	}
	
	@Override
	public Profile checkIfUserHasProfile(Long userId) {
		Query query = this.getSession().createQuery("from lwb_user_profile p where p.userOwner.id = :userId");
		query.setParameter("userId", userId);
		return (Profile) query.uniqueResult();
	}

	@Override
	public Profile getProfileByUserName(String userName) {
		Query query = this.getSession().createQuery("from lwb_user_profile p where p.userOwner.userName = :userName");
		query.setParameter("userName", userName);
		return (Profile) query.uniqueResult();
	}
	
	@Override
	public Profile getProfileByUserId(Long userId) {
		return this.checkIfUserHasProfile(userId);
	}
}
