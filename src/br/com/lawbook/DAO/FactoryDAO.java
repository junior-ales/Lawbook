package br.com.lawbook.DAO;

import br.com.lawbook.DAO.hibernate.HibernateFactoryDAO;

/**
 * @author Edilson Luiz Ales Junior
 * @version 14SEP2011-03 
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

	public abstract PostDAO getPostDAO();
}
