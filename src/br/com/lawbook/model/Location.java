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
 * @version 23NOV2011-07
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
	private static final long serialVersionUID = 4558747330416487751L;

	public Long getId() {
		return this.id;
	}
	public void setId(final Long id) {
		this.id = id;
	}
	public String getCountry() {
		return this.country;
	}
	public void setCountry(final String country) {
		this.country = country;
	}
	public String getState() {
		return this.state;
	}
	public void setState(final String state) {
		this.state = state;
	}
	public String getCity() {
		return this.city;
	}
	public void setCity(final String city) {
		this.city = city;
	}
	public String getMainAdd() {
		return this.mainAdd;
	}
	public void setMainAdd(final String mainAdd) {
		this.mainAdd = mainAdd;
	}
	public String getMainZipCode() {
		return this.mainZipCode;
	}
	public void setMainZipCode(final String mainZipCode) {
		this.mainZipCode = mainZipCode;
	}
	public String getSecAdd() {
		return this.secAdd;
	}
	public void setSecAdd(final String secAdd) {
		this.secAdd = secAdd;
	}
	public String getSecAddZipCode() {
		return this.secAddZipCode;
	}
	public void setSecAddZipCode(final String secAddZipCode) {
		this.secAddZipCode = secAddZipCode;
	}
	public String getShort() {
		return this.city + ", " + this.state;
	}
	public String getMedium() {
		return this.city + ", " + this.state + " - " + this.country;
	}
	public String getMainAddComplete() {
		return this.mainAdd + " - " + this.mainZipCode + " - " + this.city + ", " + this.state + " - " + this.country;
	}
	public String getSecAddComplete() {
		return this.secAdd + " - " + this.secAddZipCode + " - " + this.city + ", " + this.state + " - " + this.country;
	}

}
