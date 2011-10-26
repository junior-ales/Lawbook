package br.com.lawbook.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

/**
 * @author Edilson Luiz Ales Junior
 * @version 25OCT2011-01 
 * 
 */
@MappedSuperclass
public class Customer implements Serializable {
	
	@Column(length = 100)
	private String name;
	private Calendar birth;
	private Integer phone;
	@OneToOne @ForeignKey(name="FK_CUSTOMER_LOCATION")
	private Location address;
	@OneToOne @ForeignKey(name="FK_CUSTOMER_PROFILE")
	private Profile profileOwner;
	@Transient
	private static final long serialVersionUID = 6349778122693658984L;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Calendar getBirth() {
		return birth;
	}
	public void setBirth(Calendar birth) {
		this.birth = birth;
	}
	public Integer getPhone() {
		return phone;
	}
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	public Location getAddress() {
		return address;
	}
	public void setAddress(Location address) {
		this.address = address;
	}
	public Profile getProfileOwner() {
		return profileOwner;
	}
	public void setProfileOwner(Profile profileOwner) {
		this.profileOwner = profileOwner;
	}
}
