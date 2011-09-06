package br.com.lawbook.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import br.com.lawbook.model.Comment;
import br.com.lawbook.model.Location;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 04SEP2011-02 
 * 
 */

public class TablesGenerator {

	public static void main(String[] args) {
		Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
		cfg.addAnnotatedClass(User.class);
		cfg.addAnnotatedClass(Profile.class);
		cfg.addAnnotatedClass(Location.class);
		cfg.addAnnotatedClass(Post.class);
		cfg.addAnnotatedClass(Comment.class);
		SchemaExport se = new SchemaExport(cfg);
		se.create(true, true);
	}
}