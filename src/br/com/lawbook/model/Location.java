package br.com.lawbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * @author Edilson Luiz Ales Junior
 * @version 02SEP2011-01 
 */

@Entity(name="LWB_LOCATION")
public class Location {

	@Id
	@GeneratedValue
	@SequenceGenerator(name="LWB_LOCATION_SEQ", sequenceName="LWB_LOCATION_SEQ")
	private Long id;
	@Column(length = 50)
	private String country;
	@Column(length = 50)
	private String state;
	@Column(length = 50)
	private String city;
	@Column(length = 100)
	private String mainAdd;
	@Column(length = 9)
	private String mainZipCode;
	@Column(length = 100)
	private String secAdd;
	@Column(length = 9)
	private String secAddZipCode;
	
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
