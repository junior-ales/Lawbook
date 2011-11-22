package br.com.lawbook.managedbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 22NOV2011-01
 *  
 */
@ManagedBean
@RequestScoped
public class CustomerBean {

	private String userName;
	private String userId;

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		userId = (String) FacesUtil.getExternalContext().getRequestParameterMap().get("newUserId");
		return userId;
	}
	
	
	
}
