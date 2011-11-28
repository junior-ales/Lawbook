package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.HibernateException;
import org.primefaces.event.SelectEvent;

import br.com.lawbook.business.converter.AuthorityConverter;
import br.com.lawbook.business.converter.UserConverter;
import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.business.service.UserService;
import br.com.lawbook.model.Authority;
import br.com.lawbook.model.Profile;
import br.com.lawbook.model.User;
import br.com.lawbook.util.FacesUtil;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 26NOV2011-05
 *
 */
@ManagedBean
@RequestScoped
public class AdminBean implements Serializable {

	private List<User> users;
	private User chosenUser;
	private String pass;
	private String passConfirmation;
	private final List<Authority> authorities;
	private Long[] authsId;
	private Boolean disabled;
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();
	private static final Logger LOG = Logger.getLogger("AdminBean");
	private static final long serialVersionUID = -539046698174305790L;

	public AdminBean() {
		LOG.info("#### AdminBean created");
		this.chosenUser = new User();
		this.users = UserConverter.users;
		this.authorities = AuthorityConverter.authorities;
		this.authsId = new Long[this.authorities.size()];

	}

	public List<User> completeUsers(String query) {
		if (query == null) query = "";
        final List<User> results = new ArrayList<User>();
        for (final User u : this.users) {
            if (u.getUserName().startsWith(query)) {
                results.add(u);
            }
        }
        return results;
    }

	public String saveUser() {
		LOG.info("#### saveUser()");
		String outcome = "";
		try {
			this.validateUser();
			this.chosenUser.setUserName(this.chosenUser.getUserName().toLowerCase());
			UserService.getInstance().create(this.chosenUser);
			Long profileId = postProcessing();
			this.users.add(this.chosenUser);
			FacesUtil.infoMessage("=)", "User created successfully");
			outcome = "customerInfo?newUserProfileId=" + profileId + "&faces-redirect=true";
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		} catch (final NoSuchAlgorithmException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
		return outcome;
	}

	private Long postProcessing() throws IllegalArgumentException, HibernateException {
		Profile newProfile = new Profile();
		newProfile.setAvatar("http://www.lawbook.com.br/lawbook/resources/images/defaultAvatar.png");
		newProfile.setFirstName(this.chosenUser.getUserName());
		newProfile.setLastName("");
		newProfile.setLocale("pt_BR");
		newProfile.setUserOwner(UserService.getInstance().getUserById(this.chosenUser.getId()));
		PROFILE_SERVICE.create(newProfile);
		LOG.info("#### postProcessing() Profile Created");
		Profile adminProfile = new Profile();
		List<Profile> newProfileFriends = new ArrayList<Profile>();
		List<Profile> adminFriends = new ArrayList<Profile>();
		for (User user : users) {
			for (Authority auth : user.getAuthority()) {
				if (auth.getName().equalsIgnoreCase("ADMIN")) {
					adminProfile = PROFILE_SERVICE.getProfileByUserId(user.getId());
					newProfileFriends.add(adminProfile);
					adminFriends = adminProfile.getFriends();
					adminFriends.add(newProfile);
					PROFILE_SERVICE.update(adminProfile);
					LOG.info("#### postProcessing() " + adminProfile.getFirstName() + "'s Connections updated");
				}
			}
		}
		newProfile.setFriends(newProfileFriends);
		PROFILE_SERVICE.update(newProfile);
		LOG.info("#### postProcessing() " + newProfile.getFirstName() + "'s Connections added successfully");
		return newProfile.getId();
	}

	public void updateUser() {
		LOG.info("#### updateUser()");
		try {
			this.validateUser();
			UserService.getInstance().update(this.chosenUser);
			for (int i = 0; i < this.users.size(); i++) {
				if (this.users.get(i).getId().equals(this.chosenUser.getId())) {
					this.users.set(i, this.chosenUser);
				}
			}
			FacesUtil.infoMessage("=)", "User updated successfully");
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		} catch (final NoSuchAlgorithmException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	public String updateCustomerInfo() {
		LOG.info("#### updateCustomerInfo()");
		String outcome = "";
		if (this.chosenUser != null) {
			Long id = PROFILE_SERVICE.getProfileByUserId(this.chosenUser.getId()).getId();
			outcome = "customerInfo?newUserProfileId=" + id + "&faces-redirect=true";
		}
		else
			FacesUtil.warnMessage("=|", "Select an user before editing his informations");
		return outcome;
	}

	public void validateUser() throws IllegalArgumentException, NoSuchAlgorithmException {
		LOG.info("#### validateUser()");
		JavaUtil.validateParameter(this.chosenUser.getEmail(), "Email is required");
		JavaUtil.validateParameter(this.chosenUser.getUserName(), "Username is required");
		if (!this.passConfirmation.equals(this.pass)) {
			throw new IllegalArgumentException("Password and his confirmation doesn't match");
		}
		if (this.pass == null || this.pass.trim().equals("")) {
			JavaUtil.validateParameter(this.chosenUser.getPassword(), "Password is required");
		}
		else {
			if (this.pass.length() < 5) {
				throw new IllegalArgumentException("Password must have at least 5 characters");
			}
			this.chosenUser.setPassword(JavaUtil.encode(this.pass));
		}
		final ArrayList<Authority> userAuthorities = new ArrayList<Authority>();
		for (final Long id : this.authsId) {
			for (final Authority auth : this.authorities) {
				if (id == auth.getId()) userAuthorities.add(auth);
			}
		}
		this.chosenUser.setAuthority(userAuthorities);
	}

	public void handleSelect(final SelectEvent event) {
		LOG.info("#### handleSelect(final SelectEvent event)");
		final List<Authority> auths = this.chosenUser.getAuthority();
		for (int i = 0; i < auths.size(); i++) {
			this.authsId[i] = auths.get(i).getId();
		}
		this.disabled = false;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(final List<User> users) {
		this.users = users;
	}

	public User getChosenUser() {
		return this.chosenUser;
	}

	public void setChosenUser(final User chosenUser) {
		this.chosenUser = chosenUser;
	}

	public List<Authority> getAuthorities() {
		return this.authorities;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(final String pass) {
		this.pass = pass;
	}

	public String getPassConfirmation() {
		return this.passConfirmation;
	}

	public void setPassConfirmation(final String passConfirmation) {
		this.passConfirmation = passConfirmation;
	}

	public Long[] getAuthsId() {
		return this.authsId;
	}

	public void setAuthsId(final Long[] authsId) {
		this.authsId = authsId;
	}

	public String getDisabled() {
		if (this.disabled == null) this.disabled = true;
		return this.disabled.toString();
	}

}
