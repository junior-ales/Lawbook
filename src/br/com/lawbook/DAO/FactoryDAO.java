package br.com.lawbook.DAO;

public abstract class FactoryDAO {

    public static FactoryDAO getFactoryDAO() {
        return new FactoryHibernateDAO();
    }

    public abstract void beginTx();

    public abstract void cancelTx();

    public abstract void shutTx();
}
