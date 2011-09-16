package br.com.lawbook.DAO.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.lawbook.DAO.*;
import br.com.lawbook.util.HibernateUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 13SEP2011-02 
 * 
 */

public class HibernateFactoryDAO extends FactoryDAO {

    private Session session;
    private Transaction tx;

    public HibernateFactoryDAO() {
        this.session = HibernateUtil.getSession();
    }

    @Override
    public void beginTx() {
        this.tx = this.session.beginTransaction();
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
        this.session.close();
    }

	@Override
	public ProfileDAO getProfileDAO() {
		return new HibernateProfileDAO(this.session);
	}

	@Override
	public PostDAO getPostDAO() {
		return new HibernatePostDAO(this.session);
	}

	@Override
	public UserDAO getUserDAO() {
		return new HibernateUserDAO(this.session);
	}
}
