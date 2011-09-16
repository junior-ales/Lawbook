package br.com.lawbook.business;

import java.util.Calendar;

import br.com.lawbook.DAO.CommentDAO;
import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.model.Comment;

/**
 * @author Edilson Luiz Ales Junior
 * @version 16SEP2011-01
 *  
 */

public class CommentService {

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
	
	public void save(Comment comment) {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		CommentDAO dao = factory.getCommentDAO();
		factory.beginTx();
		dao.save(comment);
		factory.shutTx();
	}
	
}
