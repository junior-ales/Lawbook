package br.com.lawbook.business.service;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.lawbook.dao.ProfileDAO;
import br.com.lawbook.dao.impl.ProfileDAOImpl;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27NOV2011-16
 */
public final class ProfileService implements Serializable {

	private final ProfileDAO dao;
	private static ProfileService instance;
	private static Profile publicProfile;
	private final static Logger LOG = Logger.getLogger("ProfileService");
	private static final long serialVersionUID = -6480090150580865692L;

	private ProfileService() {
		this.dao = new ProfileDAOImpl();
	}

	public static ProfileService getInstance() {
		if (instance == null) {
			instance = new ProfileService();
		}
		return instance;
	}

	public void create(final Profile profile) throws IllegalArgumentException, HibernateException  {
		JavaUtil.validateParameter(profile, "ProfileService: create: profile");
		this.dao.create(profile);
	}

	public void update(final Profile profile) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(profile, "ProfileService: update: profile");
		this.dao.update(profile);
	}

	public Profile getAuthorizedUserProfile() throws IllegalArgumentException, HibernateException, Exception {
		final SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) throw new Exception("SecurityContext is null");

		final Authentication authentication = context.getAuthentication();
        if (authentication == null) throw new Exception("Authentication is null");

        final String username = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
        return this.getProfileByUserName(username);
	}

	public Profile getProfileById(final Long id) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(id, "ProfileService: getProfileById: id");
		return this.dao.getProfileById(id);
	}

	public Profile getProfileByUserName(final String userName) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(userName, "ProfileService: getProfileByUserName: userName");
		return this.dao.getProfileByUserName(userName);
	}

	public Profile getProfileByUserId(final Long userId) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(userId, "ProfileService: getProfileByUserId: userId");
		return this.dao.getProfileByUserId(userId);
	}

	public List<Profile> getAll() throws HibernateException {
		return this.dao.getAll();
	}

	public Profile getPublicProfile() {
		if (publicProfile == null) {
			try {
				publicProfile = this.getProfileByUserName("public");
			} catch (final HibernateException e) {
				LOG.severe(e.getMessage());
			}
		}
		return publicProfile;
	}

	public void friendship(final Profile profile1, final Profile profile2) throws IllegalArgumentException, HibernateException {
		if (profile1.getId().equals(profile2.getId()))
			throw new IllegalArgumentException("You cannot connect to yourself");

		final List<Profile> friends = profile1.getFriends();
		for (final Profile p : friends) {
			if (p.getId().equals(profile2.getId())) {
				throw new IllegalArgumentException("This user is already connected to you");
			}
		}

		friends.add(profile2);
		profile1.setFriends(friends);
		this.update(profile1);
	}

}
