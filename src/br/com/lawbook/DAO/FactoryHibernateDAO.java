package br.com.lawbook.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.lawbook.util.HibernateUtil;

public class FactoryHibernateDAO extends FactoryDAO {

    private Session session;
    private Transaction tx;

    public FactoryHibernateDAO() {
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
