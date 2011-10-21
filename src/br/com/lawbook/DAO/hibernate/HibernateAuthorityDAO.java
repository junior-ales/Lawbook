package br.com.lawbook.DAO.hibernate;

import org.hibernate.Session;

import br.com.lawbook.DAO.AuthorityDAO;
import br.com.lawbook.model.Authority;

/**
 * @author Edilson Luiz Ales Junior
 * @version 20OCT2011-01
 * 
 */
public class HibernateAuthorityDAO extends HibernateGenericDAO<Authority> implements AuthorityDAO {

	public HibernateAuthorityDAO(Session session) {
		super(session);
	}

}
