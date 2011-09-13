package br.com.lawbook.DAO;

import br.com.lawbook.DAO.hibernate.HibernateFactoryDAO;

/**
 * @author Edilson Luiz Ales Junior
 * @version 13SEP2011-02 
 * 
 */

public abstract class FactoryDAO {

    public static FactoryDAO getFactoryDAO() {
        return new HibernateFactoryDAO();
    }

    public abstract void beginTx();

    public abstract void cancelTx();

    public abstract void shutTx();

	public abstract ProfileDAO getProfileDAO();
}
