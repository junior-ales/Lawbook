package br.com.lawbook.dao.impl;

import org.hibernate.Session;

import br.com.lawbook.dao.CommentDAO;
import br.com.lawbook.model.Comment;

/**
 * @author Edilson Luiz Ales Junior
 * @version 16SEP2011-01 
 * 
 */

public class CommentDAOImpl extends GenericDAOImpl<Comment> implements CommentDAO {

	public CommentDAOImpl(Session session) {
		super(session);
	}
}
