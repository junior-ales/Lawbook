package br.com.lawbook.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.lawbook.dao.*;
import br.com.lawbook.util.HibernateUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-07
 * 
 */
public class FactoryDAOImpl extends FactoryDAO {

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
	public LocationDAO getLocationDAO() {
		return new LocationDAOImpl(this.getSession());
	}

	@Override
	public CommentDAO getCommentDAO() {
		return new CommentDAOImpl(this.getSession());
	}

	@Override
	public AuthorityDAO getAuthorityDAO() {
		return new AuthorityDAOImpl(this.getSession());
	}
	
}
