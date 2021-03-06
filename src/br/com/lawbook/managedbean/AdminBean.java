package br.com.lawbook.managedbean;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.HibernateException;
import org.primefaces.event.SelectEvent;

import br.com.lawbook.business.AuthorityService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.business.UserService;
import br.com.lawbook.model.Authority;
import br.com.lawbook.model.Profile;
import br.com.lawbook.model.User;
import br.com.lawbook.util.FacesUtil;
import br.com.lawbook.util.JavaUtil;
import br.com.lawbook.util.UserConverter;

/**
 * @author Edilson Luiz Ales Junior
 * @version 22NOV2011-04
 *
 */
@ManagedBean
@RequestScoped
public class AdminBean {

	private List<User> users;
	private User chosenUser;
	private String usernameComplete;
	private String pass;
	private String passConfirmation;
	private List<Authority> authorities;
	private Long[] authsId;
	private Boolean disabled;
	private final String imagePath = FacesUtil.getExternalContext().getRequestContextPath() + "resources/images/";
	private final static Logger LOG = Logger.getLogger("AdminBean");

	public AdminBean() {
		LOG.info("#### AdminBean created ####");
		this.chosenUser = new User();
		this.users = UserConverter.users;
		try{
			this.authorities = AuthorityService.getInstance().getAll();
			this.authsId = new Long[this.authorities.size()];
		} catch (final HibernateException e) {
			this.authorities = new ArrayList<Authority>();
			FacesUtil.errorMessage("=(", e.getMessage());
		}

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
		LOG.info("#### saveUser() ####");
		String outcome = "";
		try {
			this.validateUser();
			UserService.getInstance().create(this.chosenUser);
			final Profile profile = new Profile();
			profile.setAvatar(this.imagePath + "defaultAvatar.png");
			profile.setFirstName(this.chosenUser.getUserName());
			profile.setLastName("");
			profile.setUserOwner(UserService.getInstance().getUserById(this.chosenUser.getId()));
			ProfileService.getInstance().create(profile);
			this.users.add(this.chosenUser);
			FacesUtil.infoMessage("=)", "User created successfully");
			outcome = "customerInfo?newUserId=" + this.chosenUser.getId() + "&faces-redirect=true";
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		} catch (final NoSuchAlgorithmException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
		return outcome;
	}

	public void updateUser() {
		LOG.info("#### updateUser() ####");
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
		LOG.info("#### updateCustomerInfo() ####");
		String outcome = "";
		if (this.chosenUser != null && this.chosenUser.getId() != null)
			outcome = "customerInfo?newUserId=" + this.chosenUser.getId() + "&faces-redirect=true";
		else
			FacesUtil.warnMessage("=|", "Select an user before editing his informations");
		return outcome;
	}

	private void validateUser() throws IllegalArgumentException, NoSuchAlgorithmException {
		LOG.info("#### validateUser() ####");
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
		LOG.info("#### handleSelect(final SelectEvent event) ####");
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

	public String getUsernameComplete() {
		return this.usernameComplete;
	}

	public void setUsernameComplete(final String usernameComplete) {
		this.usernameComplete = usernameComplete;
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

	public String getImagePath() {
		return this.imagePath;
	}

	public String getDisabled() {
		if (this.disabled == null) this.disabled = true;
		return this.disabled.toString();
	}

}
