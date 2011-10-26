package br.com.lawbook.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import br.com.lawbook.model.*;

/**
 * @author Edilson Luiz Ales Junior
 * @version 25OCT2011-07
 * 
 */
public class TablesGenerator {

	public static void main(String[] args) {
		
		try {
			Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
			cfg.addAnnotatedClass(Authority.class);
			cfg.addAnnotatedClass(Comment.class);
			cfg.addAnnotatedClass(Company.class);
			cfg.addAnnotatedClass(Customer.class);
			cfg.addAnnotatedClass(Location.class);
			cfg.addAnnotatedClass(Person.class);
			cfg.addAnnotatedClass(Post.class);
			cfg.addAnnotatedClass(Profile.class);
			cfg.addAnnotatedClass(User.class);
			SchemaExport se = new SchemaExport(cfg);
			se.create(true, true);
		} catch (Throwable ex) {
			System.out.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}
