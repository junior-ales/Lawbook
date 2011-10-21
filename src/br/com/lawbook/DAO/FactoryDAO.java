package br.com.lawbook.DAO;

import br.com.lawbook.DAO.hibernate.HibernateFactoryDAO;

/**
 * @author Edilson Luiz Ales Junior
 * @version 20OCT2011-05 
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
	
	public abstract UserDAO getUserDAO();

	public abstract LocationDAO getLocationDAO();

	public abstract CommentDAO getCommentDAO();
	
	public abstract AuthorityDAO getAuthorityDAO();
}
