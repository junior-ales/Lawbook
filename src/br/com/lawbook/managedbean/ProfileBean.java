package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.hibernate.HibernateException;
import org.primefaces.model.LazyDataModel;

import br.com.lawbook.business.service.PostService;
import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 26NOV2011-12
 */
@ManagedBean
@SessionScoped
public class ProfileBean implements Serializable {
	
	private Profile authProfile;
	private Profile profileOwner;
	private String birth;
	private LazyDataModel<Post> wall;
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();
	private static final PostService POST_SERVICE = PostService.getInstance();
	private static final Logger LOG = Logger.getLogger("ProfileBean");
	private static final long serialVersionUID = -7128108390695306743L;

	public ProfileBean() {
		this.profileOwner = new Profile();
		try {
			this.setAuthProfile(PROFILE_SERVICE.getAuthorizedUserProfile());
			if (this.authProfile.getBirth() != null) {
				final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				this.birth = df.format(this.authProfile.getBirth().getTime());
			}
		} catch (Exception e) {
			FacesUtil.errorMessage("Authentication Error", "Problem with authentication process: " + e.getMessage());
		}
	}

	@PostConstruct
	public void loadLazyWall() {
		if (this.wall == null) {
			this.wall = new LazyDataModel<Post>() {
				private static final long serialVersionUID = -4238038748234463347L;

				@Override
				public List<Post> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {
					List<Post> posts = POST_SERVICE.getWall(profileOwner, authProfile, first, pageSize);
					return posts;
				}
			};
			this.wall.setRowCount(POST_SERVICE.getPostsCount());
		}
	}
	
	public void removePost(ActionEvent actionEvent) {
		if (this.profileOwner.getId().equals(this.authProfile.getId())) {
			FacesUtil.warnMessage("=|", "You cannot delete this post");
			return;
		}
		Post post = (Post) this.wall.getRowData();
		try {
			POST_SERVICE.delete(post);
			FacesUtil.infoMessage("=)", "Post deleted!");
		} catch (IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	public void addFriend(ActionEvent event) {
		try {
			PROFILE_SERVICE.friendship(this.authProfile, this.profileOwner);
			FacesUtil.infoMessage("=)", "Connection successfully added!");
		} catch (IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	public String getDisabled() {
		
		if (authProfile.getId() == profileOwner.getId()) return "true";  

		for (Profile p: this.authProfile.getFriends()) {
			if (p.getId().equals(this.profileOwner.getId())) return "true";
		}
		
		return "false";
	}
	
	public String getLocale() {
		String locale = this.authProfile.getLocale(); 
		if (locale == null || locale.equals("")) locale = "pt_BR";
		return locale;
	}
	
	public void updateProfile() {
		LOG.info("#### updateProfile()");
		try {
			this.authProfile.setBirth(getDate(this.birth));
			PROFILE_SERVICE.update(this.authProfile);
			FacesUtil.infoMessage("=)", "Profile updated successfully");
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	private static Calendar getDate(final String dateString) {
		final Calendar c = Calendar.getInstance();
		final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			c.setTime(df.parse(dateString));
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public LazyDataModel<Post> getWall() {
		return this.wall;
	}

	public Profile getAuthProfile() {
		return authProfile;
	}

	private void setAuthProfile(Profile profile) {
		this.authProfile = profile;
	}
	
	public Profile getProfileOwner() {
		return profileOwner;
	}

	public void setProfileOwner(Profile profileOwner) {
		this.profileOwner = profileOwner;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
	
}
