package br.com.lawbook.DAO.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.util.HibernateUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 04SEP2011-01 
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
}
