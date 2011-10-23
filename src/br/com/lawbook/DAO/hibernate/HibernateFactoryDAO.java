package br.com.lawbook.DAO.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.lawbook.DAO.*;
import br.com.lawbook.util.HibernateUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 22OCT2011-06
 * 
 */
public class HibernateFactoryDAO extends FactoryDAO {

    private Transaction tx;

    public Session getSession() {
		return HibernateUtil.getSession();
	}

	@Override
    public void beginTx() {
        this.tx = this.getSession().beginTransaction();
    }

    @Override
    public void cancelTx() {
        this.tx.rollback();
        this.tx = null;
    }

    @Override
    public void shutTx() {
    	if (this.tx != null) {
            this.tx.commit();
        }
    }

	@Override
	public ProfileDAO getProfileDAO() {
		return new HibernateProfileDAO(this.getSession());
	}

	@Override
	public PostDAO getPostDAO() {
		return new HibernatePostDAO(this.getSession());
	}

	@Override
	public UserDAO getUserDAO() {
		return new HibernateUserDAO(this.getSession());
	}

	@Override
	public LocationDAO getLocationDAO() {
		return new HibernateLocationDAO(this.getSession());
	}

	@Override
	public CommentDAO getCommentDAO() {
		return new HibernateCommentDAO(this.getSession());
	}

	@Override
	public AuthorityDAO getAuthorityDAO() {
		return new HibernateAuthorityDAO(this.getSession());
	}
	
}
