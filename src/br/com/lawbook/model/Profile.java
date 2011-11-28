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
 * @version 28NOV2011-19
 * 
 */
@Entity(name = "lwb_user_profile")
public class Profile implements Serializable {

	@Id
	@SequenceGenerator(name = "lwb_user_profile_seq_id", sequenceName = "lwb_user_profile_seq_id", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "lwb_user_profile_seq_id", strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToOne
	@ForeignKey(name = "FK_USER_PROFILE_USER")
	@JoinColumn(name = "user_id", unique = true, nullable = false)
	private User userOwner;
	@Column(length = 50)
	private String firstName;
	@Column(length = 50)
	private String lastName;
	private Calendar birth;
	@ManyToOne
	@ForeignKey(name = "FK_USER_PROFILE_LOCATION")
	@JoinColumn(name = "location_id")
	private Location location;
	@Column(length = 255, name = "about_me")
	private String aboutMe;
	@ManyToMany
	@ForeignKey(name = "FK_USER_PROFILE_FRIEND_LIST", inverseName = "FK_FRIEND_LIST_USER_PROFILE")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "lwb_friends", joinColumns = { @JoinColumn(name = "user_profile_id") }, inverseJoinColumns = { @JoinColumn(name = "friend_id") })
	private List<Profile> friends;
	@Column(length = 255)
	private String avatar;
	@Column(length = 5)
	private String locale;
	@Column
	private Long cpf;
	@Column
	private Long rg;
	@Column(length = 100, name = "orgao_expedidor")
	private String orgaoExpedidor;
	@Column(length = 1)
	private String gender;
	@Column(length = 100, name = "marital_status")
	private String maritalStatus;
	@Column
	private Long phone;
	@Column
	private Long cnpj;
	@Transient
	private static final long serialVersionUID = -8954930935167848226L;

	public Long getCpf() {
		return this.cpf;
	}

	public void setCpf(final Long cpf) {
		this.cpf = cpf;
	}

	public Long getRg() {
		return this.rg;
	}

	public void setRg(final Long rg) {
		this.rg = rg;
	}

	public String getOrgaoExpedidor() {
		return this.orgaoExpedidor;
	}

	public void setOrgaoExpedidor(final String orgaoExpedidor) {
		this.orgaoExpedidor = orgaoExpedidor;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(final String gender) {
		this.gender = gender;
	}

	public String getMaritalStatus() {
		return this.maritalStatus;
	}

	public void setMaritalStatus(final String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public User getUserOwner() {
		return this.userOwner;
	}

	public void setUserOwner(final User userOwner) {
		this.userOwner = userOwner;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public Calendar getBirth() {
		return this.birth;
	}

	public void setBirth(final Calendar birth) {
		if (Calendar.getInstance().compareTo(birth) < 0) {
			throw new IllegalArgumentException(
					"A data de nascimento deve ser menor que a data de hoje");
		}
		this.birth = birth;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}

	public String getAboutMe() {
		return this.aboutMe;
	}

	public void setAboutMe(final String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public List<Profile> getFriends() {
		return this.friends;
	}

	public void setFriends(final List<Profile> friends) {
		this.friends = friends;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(final String avatar) {
		this.avatar = avatar;
	}

	public String getLocale() {
		return this.locale;
	}

	public void setLocale(final String locale) {
		this.locale = locale;
	}

	public Long getPhone() {
		return this.phone;
	}

	public void setPhone(final Long phone) {
		this.phone = phone;
	}

	public Long getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(final Long cnpj) {
		this.cnpj = cnpj;
	}

}
