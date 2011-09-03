package br.com.lawbook.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import br.com.lawbook.model.Location;
import br.com.lawbook.model.Profile;
import br.com.lawbook.model.User;

public class TablesGenerator {

	public static void main(String[] args) {
		Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
		cfg.addAnnotatedClass(User.class);
		cfg.addAnnotatedClass(Profile.class);
		cfg.addAnnotatedClass(Location.class);
		SchemaExport se = new SchemaExport(cfg);
		se.create(true, true);
	}
}