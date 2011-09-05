package br.com.lawbook.DAO;

import br.com.lawbook.DAO.hibernate.HibernateFactoryDAO;

public abstract class FactoryDAO {

    public static FactoryDAO getFactoryDAO() {
        return new HibernateFactoryDAO();
    }

    public abstract void beginTx();

    public abstract void cancelTx();

    public abstract void shutTx();
}
