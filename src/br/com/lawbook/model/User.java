package br.com.lawbook.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

/**
 * @author Edilson Luiz Ales Junior
 * @version 30SEP2011-09 
 * 
 */
@Entity(name="lwb_user")
public class User implements Serializable {
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(length = 100)
	private String userName;
	@Column(length = 100)
	private String email;
	@Column(length = 100)
	private String password;
	@ManyToMany
	@JoinTable(name="lwb_user_authority")
	private List<Authority> authority;
	private boolean enable;
	@Transient
	private static final long serialVersionUID = 1L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Authority> getAuthority() {
		return authority;
	}
	public void setAuthority(List<Authority> authority) {
		this.authority = authority;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}
