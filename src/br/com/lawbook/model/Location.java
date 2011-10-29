package br.com.lawbook.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * @author Edilson Luiz Ales Junior
 * @version 20OCT2011-06 
 */
@Entity(name="lwb_location")
public class Location implements Serializable {

	@Id
	@SequenceGenerator(name="lwb_location_seq_id", sequenceName="lwb_location_seq_id",allocationSize=1,initialValue=1)
    @GeneratedValue(generator="lwb_location_seq_id", strategy= GenerationType.SEQUENCE)
	private Long id;
	@Column(length = 50)
	private String country;
	@Column(length = 50)
	private String state;
	@Column(length = 50)
	private String city;
	@Column(length = 100, name="main_address")
	private String mainAdd;
	@Column(length = 9, name="main_address_zipcode")
	private String mainZipCode;
	@Column(length = 100, name="sec_address")
	private String secAdd;
	@Column(length = 9, name="sec_address_zipcode")
	private String secAddZipCode;
	@Transient
	private static final long serialVersionUID = 6916990507563022210L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getMainAdd() {
		return mainAdd;
	}
	public void setMainAdd(String mainAdd) {
		this.mainAdd = mainAdd;
	}
	public String getMainZipCode() {
		return mainZipCode;
	}
	public void setMainZipCode(String mainZipCode) {
		this.mainZipCode = mainZipCode;
	}
	public String getSecAdd() {
		return secAdd;
	}
	public void setSecAdd(String secAdd) {
		this.secAdd = secAdd;
	}
	public String getSecAddZipCode() {
		return secAddZipCode;
	}
	public void setSecAddZipCode(String secAddZipCode) {
		this.secAddZipCode = secAddZipCode;
	}
}
