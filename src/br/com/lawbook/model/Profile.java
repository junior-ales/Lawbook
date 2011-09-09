package br.com.lawbook.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author Edilson Luiz Ales Junior
 * @version 09SEP2011-06
 */

@Entity(name = "lwb_user_profile")
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
	@JoinColumn(name = "location_id")
	private Location location;
	@Column(length = 255, name = "about_me")
	private String aboutMe;
	@OneToOne
	@JoinColumn(name = "wall_id")
	private List<Post> wall;
	@OneToOne
	@JoinColumn(name="friends_list_id")
	private List<User> friendsList;
	@OneToOne
	@JoinColumn(name = "stream_id")
	private List<Post> stream;
	
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
		if (Calendar.getInstance().compareTo(birth) < 0) {
			throw new IllegalArgumentException("A data de nascimento deve ser menor que a data de hoje");
		}
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

	public List<Post> getWall() {
		return wall;
	}

	public void setWall(List<Post> wall) {
		this.wall = wall;
	}

	public List<User> getFriendsList() {
		return friendsList;
	}

	public void setFriendsList(List<User> friendsList) {
		this.friendsList = friendsList;
	}

	public List<Post> getStream() {
		return stream;
	}

	public void setStream(List<Post> stream) {
		this.stream = stream;
	}

	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return "Profile [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", birth=" + df.format(birth.getTime()) + ", location=" + location
				+ ", aboutMe=" + aboutMe + ", wall=" + wall + ", friendsList="
				+ friendsList + ", stream=" + stream + "]";
	}
}
