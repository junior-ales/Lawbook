package br.com.lawbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Edilson Luiz Ales Junior
 * @version 02SEP2011-01 
 * 
 */
@Entity
@Table(name="LWB_USER")
public class User {
	
	@Id
	@GeneratedValue
	@SequenceGenerator(name="LWB_USER_SEQ", sequenceName="LWB_USER_SEQ")
	private Long id;
	@Column(length = 100)
	private String userName;
	@Column(length = 100)
	private String email;
	@Column(length = 100)
	private String password;
	@OneToOne
	@JoinColumn(name="perfilId")
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
	public Profile getPerfil() {
		return profile;
	}
	public void setPerfil(Profile perfil) {
		this.profile = perfil;
	}
	
}
