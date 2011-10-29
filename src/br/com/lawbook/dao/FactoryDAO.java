package br.com.lawbook.dao;

import br.com.lawbook.dao.impl.FactoryDAOImpl;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-06 
 * 
 */
public abstract class FactoryDAO {

    public static FactoryDAO getFactoryDAO() {
        return new FactoryDAOImpl();
    }

    public abstract void beginTx();

    public abstract void cancelTx();

    public abstract void shutTx();

	public abstract LocationDAO getLocationDAO();

	public abstract CommentDAO getCommentDAO();
}
