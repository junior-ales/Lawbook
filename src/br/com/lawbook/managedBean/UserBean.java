package br.com.lawbook.managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.lawbook.business.UserService;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27OCT2011-04
 *  
 */
@ManagedBean
@RequestScoped
public class UserBean {

	private UserService service;
	private String userName;
	private String password;
	private String passwordConfirmation;
	private String email;

	public UserBean() {
		this.service = UserService.getInstance();
	}

	public UserService getService() {
		return service;
	}

	public void setService(UserService service) {
		this.service = service;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
