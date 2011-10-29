package br.com.lawbook.dao;

import br.com.lawbook.dao.impl.FactoryDAOImpl;

/**
 * @author Edilson Luiz Ales Junior
 * @version 20OCT2011-05 
 * 
 */

public abstract class FactoryDAO {

    public static FactoryDAO getFactoryDAO() {
        return new FactoryDAOImpl();
    }

    public abstract void beginTx();

    public abstract void cancelTx();

    public abstract void shutTx();

	public abstract PostDAO getPostDAO();
	
	public abstract LocationDAO getLocationDAO();

	public abstract CommentDAO getCommentDAO();
	
	public abstract AuthorityDAO getAuthorityDAO();
}
