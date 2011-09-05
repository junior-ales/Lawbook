package br.com.lawbook.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Edilson Luiz Ales Junior
 * @version 04SEP2011-02 
 */

@Entity(name="LWB_USER_PROFILE")
public class Profile {
	
	@Id 
	@GeneratedValue
	private Long id;
	@Column(length = 50)
	private String firstName;
	@Column(length = 50)
	private String lastName;
	private Calendar birth;
	@ManyToOne
	@JoinColumn(name="location_id")
	private Location location;
	@Column(length = 255, name="about_me")
	private String aboutMe;
	@OneToMany
	@JoinTable(name="lwb_wall_messages")
	private List<Messages> wall;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Calendar getBirth() {
		return birth;
	}
	public void setBirth(Calendar birth) {
		this.birth = birth;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public List<Messages> getWall() {
		return wall;
	}
	public void setWall(List<Messages> wall) {
		this.wall = wall;
	}
}
