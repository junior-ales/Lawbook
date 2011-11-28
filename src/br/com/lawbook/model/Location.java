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
 * @version 28NOV2011-08
 */
@Entity(name="lwb_location")
public class Location implements Serializable {

	@Id
	@SequenceGenerator(name="lwb_location_seq_id", sequenceName="lwb_location_seq_id",allocationSize=1,initialValue=1)
    @GeneratedValue(generator="lwb_location_seq_id", strategy= GenerationType.SEQUENCE)
	private Long id;
	@Column(length = 50)
	private String name;
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
	@Transient
	private static final long serialVersionUID = -7306040757981475421L;

	public Long getId() {
		return this.id;
	}
	public void setId(final Long id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
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
	public String getShort() {
		return this.city + ", " + this.state;
	}
	public String getMedium() {
		return this.city + ", " + this.state + " - " + this.country;
	}
	public String getMainAddComplete() {
		return this.mainAdd + " - " + this.mainZipCode + " - " + this.city + ", " + this.state + " - " + this.country;
	}

}
