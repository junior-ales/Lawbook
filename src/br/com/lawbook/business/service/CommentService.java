package br.com.lawbook.business.service;

import java.util.Calendar;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.CommentDAO;
import br.com.lawbook.dao.impl.CommentDAOImpl;
import br.com.lawbook.model.Comment;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-01
 *  
 */
public final class CommentService {

	private static CommentService instance;
	public Long senderId;
	public String content;
	public Calendar dateTime;
	
	private CommentService() {
	}
	
	public static CommentService getInstance() {
		if (instance == null) {
			instance = new CommentService();
		}
		return instance;
	}
	
	public void save(final Comment comment) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(comment, "CommentService: save: comment");
		final CommentDAO dao = new CommentDAOImpl();
		dao.save(comment);
	}
	
	public void delete(final Comment comment) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(comment, "CommentService: delete: comment");
		final CommentDAO dao = new CommentDAOImpl();
		dao.delete(comment);
	}
	
}
