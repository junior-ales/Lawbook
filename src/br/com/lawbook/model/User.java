package br.com.lawbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author Edilson Luiz Ales Junior
 * @version 08SEP2011-03 
 * 
 */
@Entity(name="lwb_user")
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(length = 100)
	private String userName;
	@Column(length = 100)
	private String email;
	@Column(length = 100)
	private String password;
	@OneToOne
	@JoinColumn(name="perfil_id")
	private Profile profile;
	
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
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile perfil) {
		this.profile = perfil;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", email=" + email
				+ ", password=" + password + ", profile=" + profile + "]";
	}
	
}
