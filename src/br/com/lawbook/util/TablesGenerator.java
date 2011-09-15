package br.com.lawbook.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 12SEP2011-04 
 * 
 */

public class TablesGenerator {

	public static void main(String[] args) {
//		Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
//		cfg.addAnnotatedClass(User.class);
//		cfg.addAnnotatedClass(Profile.class);
//		cfg.addAnnotatedClass(Location.class);
//		cfg.addAnnotatedClass(Post.class);
//		cfg.addAnnotatedClass(Comment.class);
//		SchemaExport se = new SchemaExport(cfg);
//		se.create(true, true);
		
////		User u;
////		for (int i = 0; i < 10; i++) {
////			u = new User();
////			u.setEmail("email"+i);
////			u.setPassword("password"+i);
////			u.setProfile(null);
////			u.setUserName("userName"+i);
////			session.merge(u);
////		}
//		
////		 List <User> users = session.createCriteria(User.class).list();
////		 for (User u : users) {
////			 System.out.println(u.getUserName());
////		 }
//		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<String> userNames = new ArrayList<String>();
		userNames.add("userName2");
		userNames.add("userName0");
		userNames.add("userName7");
		Query query = session.createQuery("from lwb_user u where u.userName = :userName1 or u.userName = :userName2");
		query.setParameter("userName1", userNames.get(0));
		query.setParameter("userName2", userNames.get(1));
		
		@SuppressWarnings("unchecked")
		List<User> users = query.list();
		
		 for (User u : users) {
			 System.out.println(u.getUserName());
		 }
		tx.commit();
		session.close();
	}
}











