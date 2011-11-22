package br.com.lawbook.managedbean;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.HibernateException;
import org.primefaces.event.SelectEvent;

import br.com.lawbook.business.AuthorityService;
import br.com.lawbook.business.UserService;
import br.com.lawbook.model.Authority;
import br.com.lawbook.model.User;
import br.com.lawbook.util.FacesUtil;
import br.com.lawbook.util.JavaUtil;
import br.com.lawbook.util.UserConverter;

/**
 * @author Edilson Luiz Ales Junior
 * @version 22NOV2011-03
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
//	private final static Logger LOG = Logger.getLogger("AdminBean");

	public AdminBean() {
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
		this.validateUser();
		try {
			UserService.getInstance().create(this.chosenUser);
			this.users.add(this.chosenUser);
			// TODO create a profile to new user
			FacesUtil.infoMessage("=)", "User created successfully");
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
			return "";
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
			return "";
		}
		return "newUserInfo?newUserId=" + this.chosenUser.getId() + "&faces-redirect=true";
	}

	public void updateUser() {
		this.validateUser();
		try {
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
		}
	}

	private void validateUser() {
		try {
			JavaUtil.validateParameter(this.chosenUser.getEmail(), "AdminBean: validateUser: chosenUser.getEmail()");
			JavaUtil.validateParameter(this.chosenUser.getUserName(), "AdminBean: validateUser: chosenUser.getUserName()");
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", "Username and email are required");
			return;
		}
		if (!this.passConfirmation.equals(this.pass)) {
			FacesUtil.warnMessage("=|", "Password and his confirmation doesn't match");
			return;
		}
		try {
			if (this.pass != null && !this.pass.trim().equals("")) {
				if (this.pass.length() < 5) {
					FacesUtil.warnMessage("=|", "Password must have at least 5 characters");
					return;
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
		} catch (final NoSuchAlgorithmException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public void handleSelect(final SelectEvent event) {

		final List<Authority> auths = this.chosenUser.getAuthority();
		for (int i = 0; i < auths.size(); i++) {
			this.authsId[i] = auths.get(i).getId();
		}
		FacesUtil.infoMessage("=)", Arrays.toString(this.authsId));
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

	public void setAuthorities(final List<Authority> authorities) {
		this.authorities = authorities;
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

}
