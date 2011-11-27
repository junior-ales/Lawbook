package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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
	private Profile publicProfile;
	private Date birth;
	private LazyDataModel<Post> wall;
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();
	private static final PostService POST_SERVICE = PostService.getInstance();
	private static final Logger LOG = Logger.getLogger("ProfileBean");
	private static final long serialVersionUID = -7128108390695306743L;

	public ProfileBean() {
		this.profileOwner = new Profile();
		try {
			this.authProfile = PROFILE_SERVICE.getAuthorizedUserProfile();
			this.publicProfile = PROFILE_SERVICE.getPublicProfile();
			if (this.authProfile.getBirth() != null) {
				this.birth = this.authProfile.getBirth().getTime();
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
		Post post = new Post();
		if (!this.profileOwner.getId().equals(this.authProfile.getId())) {
			post = (Post) this.wall.getRowData();
			if (!this.authProfile.getId().equals(post.getSender().getId())) {
				FacesUtil.warnMessage("=|", "You cannot delete this post");
				return;
			}
		}
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
		final Calendar auxDate = Calendar.getInstance();
		auxDate.setTime(this.birth);
		this.authProfile.setBirth(auxDate);
		try {
			PROFILE_SERVICE.update(this.authProfile);
			FacesUtil.infoMessage("=)", "Profile updated successfully");
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	public LazyDataModel<Post> getWall() {
		return this.wall;
	}

	public Profile getProfileOwner() {
		return profileOwner;
	}

	public void setProfileOwner(Profile profileOwner) {
		this.profileOwner = profileOwner;
	}
	
	public Profile getAuthProfile() {
		return authProfile;
	}
	
	public Profile getPublicProfile() {
		return publicProfile;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
}
