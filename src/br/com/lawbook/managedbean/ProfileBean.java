package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.hibernate.HibernateException;
import org.primefaces.model.LazyDataModel;

import br.com.lawbook.business.service.PostService;
import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.business.service.UserService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.model.User;
import br.com.lawbook.util.FacesUtil;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 14DEC2011-13
 */
@ManagedBean
@SessionScoped
public class ProfileBean implements Serializable {

	private Date birth;
	private String pass;
	private Profile authProfile;
	private Profile profileOwner;
	private Profile publicProfile;
	private String passConfirmation;
	private LazyDataModel<Post> wall;
	private static final long serialVersionUID = -3899574847108383874L;
	private static final UserService USER_SERVICE = UserService.getInstance();
	private static final PostService POST_SERVICE = PostService.getInstance();
	private static final Logger LOG = Logger.getLogger("br.com.lawbook.managedbean");
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();
	private ResourceBundle rs;

	public ProfileBean() {
		this.profileOwner = new Profile();
		this.rs = ResourceBundle.getBundle("br.com.lawbook.util.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		try {
			this.authProfile = PROFILE_SERVICE.getAuthorizedUserProfile();
			this.publicProfile = PROFILE_SERVICE.getPublicProfile();
			if (this.authProfile.getBirth() != null) {
				this.birth = this.authProfile.getBirth().getTime();
			}
		} catch (final Exception e) {
			FacesUtil.errorMessage("Authentication Error", "Problem with authentication process: " + e.getMessage());
		}
		LOG.info(this.getClass().getSimpleName() + ": ManagedBean Created" );
	}

	@PostConstruct
	public void loadLazyWall() {
		LOG.info(this.getClass().getSimpleName() + ": loadLazyWall()");
		if (this.wall == null) {
			this.wall = new LazyDataModel<Post>() {
				private static final long serialVersionUID = -4238038748234463347L;

				@Override
				public List<Post> load(final int first, final int pageSize, final String sortField, final boolean sortOrder, final Map<String, String> filters) {
					final List<Post> posts = POST_SERVICE.getWall(ProfileBean.this.profileOwner, ProfileBean.this.authProfile, first, pageSize);
					return posts;
				}
			};
			this.wall.setRowCount(POST_SERVICE.getPostsCount());
		}
	}

	public void removePost(final ActionEvent actionEvent) {
		LOG.info(this.getClass().getSimpleName() + ": removePost(ActionEvent event)");
		Post post = new Post();
		if (!this.profileOwner.getId().equals(this.authProfile.getId())) {
			post = (Post) this.wall.getRowData();
			if (!this.authProfile.getId().equals(post.getSender().getId())) {
				FacesUtil.warnMessage("=|", this.rs.getString("msg_notDeletable"));
				return;
			}
		}
		try {
			POST_SERVICE.delete(post);
			FacesUtil.infoMessage("=)", this.rs.getString("msg_deleted"));
		} catch (final IllegalArgumentException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public void addFriend(final ActionEvent event) {
		LOG.info(this.getClass().getSimpleName() + ": addFriend(ActionEvent event)");
		try {
			PROFILE_SERVICE.friendship(this.authProfile, this.profileOwner);
			this.authProfile = PROFILE_SERVICE.getAuthorizedUserProfile();
			LOG.info(this.getClass().getSimpleName() + ": updateProfile()");
			FacesUtil.infoMessage("=)", this.rs.getString("msg_connAdded"));
		} catch (final IllegalArgumentException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public String getDisabled() {
		if (this.authProfile.getId() == this.profileOwner.getId()) return "true";
		for (final Profile p: this.authProfile.getFriends()) {
			if (p.getId().equals(this.profileOwner.getId())) return "true";
		}
		return "false";
	}

	public String getLocale() {
		String locale = this.authProfile.getLocale();
		if (locale == null || locale.trim().isEmpty()) locale = "pt_BR";
		return locale;
	}

	public void updateProfile() {
		LOG.info(this.getClass().getSimpleName() + ": updateProfile()");
		final Calendar auxDate = Calendar.getInstance();
		auxDate.setTime(this.birth);
		this.authProfile.setBirth(auxDate);
		try {
			PROFILE_SERVICE.update(this.authProfile);
			this.rs = ResourceBundle.getBundle("br.com.lawbook.util.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
			FacesUtil.infoMessage("=)", this.rs.getString("msg_profileUpdatedSuccess"));
		} catch (final IllegalArgumentException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public void updateUser() {
		LOG.info(this.getClass().getSimpleName() + ": updateUser()");
		try {
			if (!this.passConfirmation.equals(this.pass)) {
				FacesUtil.warnMessage("=|", this.rs.getString("msg_passDifferent"));
				return;
			}
			final User user = this.authProfile.getUserOwner();
			if (this.pass == null || this.pass.trim().equals("")) {
				JavaUtil.validateParameter(user.getPassword(), this.rs.getString("msg_reqPass"));
			}
			else {
				if (this.pass.length() < 5) {
					throw new IllegalArgumentException(this.rs.getString("msg_reqMinLength"));
				}
				user.setPassword(JavaUtil.encode(this.pass));
			}
			USER_SERVICE.update(user);
			LOG.info(this.getClass().getSimpleName() + ": User password updated successfully");
			this.rs = ResourceBundle.getBundle("br.com.lawbook.util.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
			FacesUtil.infoMessage("=)", this.rs.getString("msg_userUpdatedSuccess"));
		} catch (final IllegalArgumentException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.errorMessage("=(", e.getMessage());
		} catch (final NoSuchAlgorithmException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public LazyDataModel<Post> getWall() {
		return this.wall;
	}

	public Profile getProfileOwner() {
		return this.profileOwner;
	}

	public void setProfileOwner(final Profile profileOwner) {
		this.profileOwner = profileOwner;
	}

	public Profile getAuthProfile() {
		return this.authProfile;
	}

	public Profile getPublicProfile() {
		return this.publicProfile;
	}

	public Date getBirth() {
		return this.birth;
	}

	public void setBirth(final Date birth) {
		this.birth = birth;
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

}
