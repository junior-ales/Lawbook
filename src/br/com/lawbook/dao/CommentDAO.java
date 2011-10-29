package br.com.lawbook.dao;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Comment;


/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-02
 * 
 */
public interface CommentDAO {

	void save(Comment comment) throws HibernateException; 

	void delete(Comment comment) throws HibernateException;
}
