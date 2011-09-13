package br.com.lawbook.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * @author Edilson Luiz Ales Junior
 * @version 12SEP2011-09
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
	@ManyToMany
	@JoinTable(name="lwb_friends_list")
	private List<Profile> friendsList;
	
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

	public List<Profile> getFriendsList() {
		return friendsList;
	}

	public void setFriendsList(List<Profile> friendsList) {
		this.friendsList = friendsList;
	}

	@Override
	public String toString() {
		String friends = "";
		for (Profile p : this.friendsList) {
			friends += " " + p.getFirstName();
		}
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return "Profile [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", birth=" + df.format(birth.getTime()) + ", locationId=" + location.getId()
				+ ", aboutMe=" + aboutMe + ", friendsList=" + friends + "]";
	}
}
