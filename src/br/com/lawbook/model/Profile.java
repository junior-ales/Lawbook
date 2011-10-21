package br.com.lawbook.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * @author Edilson Luiz Ales Junior
 * @version 20OCT2011-14
 */

@Entity(name = "lwb_user_profile")
public class Profile implements Serializable {

	@Id
	@SequenceGenerator(name="lwb_user_profile_seq_id", sequenceName="lwb_user_profile_seq_id",allocationSize=1,initialValue=1)
    @GeneratedValue(generator="lwb_user_profile_seq_id", strategy= GenerationType.SEQUENCE)
	private Long id;
	@OneToOne
	@JoinColumn(name="user_id")
	private User userOwner;
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
	@Column(length = 255)
	private String avatar;
	@Transient
	private static final long serialVersionUID = 1156277173284535028L;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUserOwner() {
		return userOwner;
	}

	public void setUserOwner(User userOwner) {
		this.userOwner = userOwner;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
