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

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author Edilson Luiz Ales Junior
 * @version 10NOV2011-18
 * 
 */
@Entity(name = "lwb_user_profile")
public class Profile implements Serializable {

	@Id
	@SequenceGenerator(name="lwb_user_profile_seq_id", sequenceName="lwb_user_profile_seq_id",allocationSize=1,initialValue=1)
    @GeneratedValue(generator="lwb_user_profile_seq_id", strategy= GenerationType.SEQUENCE)
	private Long id;
	@OneToOne @ForeignKey(name="FK_USER_PROFILE_USER")
	@JoinColumn(name="user_id", unique=true, nullable=false)
	private User userOwner;
	@Column(length = 50)
	private String firstName;
	@Column(length = 50)
	private String lastName;
	private Calendar birth;
	@ManyToOne @ForeignKey(name="FK_USER_PROFILE_LOCATION")
	@JoinColumn(name = "location_id")
	private Location location;
	@Column(length = 255, name = "about_me")
	private String aboutMe;
	@ManyToMany @ForeignKey(name="FK_USER_PROFILE_FRIEND_LIST", inverseName="FK_FRIEND_LIST_USER_PROFILE") @LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="lwb_friends", joinColumns = { @JoinColumn(name = "user_profile_id") }, inverseJoinColumns = { @JoinColumn(name = "friend_id") })
	private List<Profile> friends;
	@Column(length = 255)
	private String avatar;
	@Column(length = 5)
	private String locale;
	@Transient
	private static final long serialVersionUID = -514526194524102776L;
	
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

	public List<Profile> getFriends() {
		return friends;
	}

	public void setFriends(List<Profile> friends) {
		this.friends = friends;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

}
