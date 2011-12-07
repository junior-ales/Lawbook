package br.com.lawbook.business.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.lawbook.dao.UserDAO;
import br.com.lawbook.dao.impl.UserDAOImpl;
import br.com.lawbook.model.User;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27NOV2011-12
 *
 */
public final class UserService  {

	private final UserDAO dao;
	private static UserService instance;

	private UserService() {
		this.dao = new UserDAOImpl();
	}

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	public void create(final User user) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(user, "UserService: create: user");
		this.dao.create(user);
	}

	public void update(final User user) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(user, "UserService: update: user");
		this.dao.update(user);
	}

	public List<User> getAll() throws HibernateException {
		return this.dao.getAll();
	}

	public User getUserById(final Long userId) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(userId, "UserService: getUserById: userId");
		return this.dao.getUserById(userId);
	}

	public User getUserByUserName(final String userName) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(userName, "UserService: getUserByUserName: userName");
		return this.dao.getUserByUserName(userName);
	}

	public User getAuthorizedUser() throws IllegalArgumentException, HibernateException, Exception {

		final SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) throw new Exception("SecurityContext is null");

		final Authentication authentication = context.getAuthentication();
        if (authentication == null) throw new Exception("Authentication is null");

        final String username = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
        return this.getUserByUserName(username);
	}
}
