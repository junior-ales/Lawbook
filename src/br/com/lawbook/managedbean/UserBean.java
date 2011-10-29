package br.com.lawbook.managedbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.lawbook.business.UserService;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OCT2011-05
 *  
 */
@ManagedBean
@RequestScoped
public class UserBean implements Serializable {

	private UserService service;
	private String userName;
	private String password;
	private String passConfirmation;
	private String email;
	private static final long serialVersionUID = -234802806543778170L;

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
		return passConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passConfirmation = passwordConfirmation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
