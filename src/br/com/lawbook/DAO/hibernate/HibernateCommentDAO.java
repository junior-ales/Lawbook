package br.com.lawbook.dao.hibernate;

import org.hibernate.Session;

import br.com.lawbook.dao.CommentDAO;
import br.com.lawbook.model.Comment;

/**
 * @author Edilson Luiz Ales Junior
 * @version 16SEP2011-01 
 * 
 */

public class HibernateCommentDAO extends HibernateGenericDAO<Comment> implements CommentDAO {

	public HibernateCommentDAO(Session session) {
		super(session);
	}
}
