package br.com.lawbook.managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.lawbook.business.UserService;

/**
 * @author Edilson Luiz Ales Junior
 * @version 15SEP2011-01
 *  
 */
@ManagedBean
@RequestScoped
public class UserBean {

	private UserService service;

	private UserBean() {
		this.service = UserService.getInstance();
	}
	
	public boolean createUser(String userName, String password, String email) {
		// TODO validate userName, password, email 
		return service.createUser(userName, password, email);
	}
	
}
