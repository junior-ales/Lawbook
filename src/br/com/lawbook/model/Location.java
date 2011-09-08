package br.com.lawbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Edilson Luiz Ales Junior
 * @version 08SEP2011-03 
 */

@Entity(name="lwb_location")
public class Location {

	@Id
	@GeneratedValue
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
	@Override
	public String toString() {
		return "Location [id=" + id + ", country=" + country + ", state="
				+ state + ", city=" + city + ", mainAdd=" + mainAdd
				+ ", mainZipCode=" + mainZipCode + ", secAdd=" + secAdd
				+ ", secAddZipCode=" + secAddZipCode + "]";
	}
}
