package br.com.lawbook.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * @author Edilson Luiz Ales Junior
 * @version 23OCT2011-12 
 * 
 */
@Entity(name="lwb_user")
public class User implements Serializable {
	
	@Id
	@SequenceGenerator(name="lwb_user_seq_id", sequenceName="lwb_user_seq_id",allocationSize=1,initialValue=1)
    @GeneratedValue(generator="lwb_user_seq_id", strategy= GenerationType.SEQUENCE)
	@Column(name="user_id")
	private Long id;
	@Column(length = 100, nullable=false)
	private String userName;
	@Column(length = 100, nullable=false)
	private String email;
	@Column(length = 100, nullable=false)
	private String password;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="lwb_user_authority", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "authority_id") })
	private List<Authority> authority;
	private boolean enable;
	@Transient
	private static final long serialVersionUID = -6082705591536638755L;
	
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
