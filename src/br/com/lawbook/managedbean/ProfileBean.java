package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.hibernate.HibernateException;
import org.primefaces.model.LazyDataModel;

import br.com.lawbook.business.PostService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 18NOV2011-11
 */
@ManagedBean
@SessionScoped
public class ProfileBean implements Serializable {
	
	private Profile authProfile;
	private Profile profileOwner;
	private LazyDataModel<Post> wall;
	private ProfileService profileService;
	private PostService postService;
	private static final long serialVersionUID = 4305913075799591127L;

	public ProfileBean() {
		this.profileOwner = new Profile();
		this.profileService = ProfileService.getInstance();
		try {
			this.setAuthProfile(this.profileService.getAuthorizedUserProfile());
		} catch (Exception e) {
			FacesUtil.errorMessage("Authentication Error", "Problem with authentication process: " + e.getMessage());
		}
	}

	@PostConstruct
	public void loadLazyWall() {
		if (this.wall == null) {
			this.profileService = ProfileService.getInstance();
			this.postService = PostService.getInstance();
			this.wall = new LazyDataModel<Post>() {
				private static final long serialVersionUID = -4238038748234463347L;

				@Override
				public List<Post> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {
					List<Post> posts = postService.getWall(profileOwner, authProfile, first, pageSize);
					return posts;
				}
			};
			this.wall.setRowCount(this.postService.getPostsCount());
		}
	}
	
	public void removePost(ActionEvent actionEvent) {
		if (this.profileOwner.getId().equals(this.authProfile.getId())) {
			FacesUtil.warnMessage("=|", "You cannot delete this post");
			return;
		}
		Post post = (Post) this.wall.getRowData();
		try {
			this.postService.delete(post);
			FacesUtil.infoMessage("=)", "Post deleted!");
		} catch (IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	public void addFriend(ActionEvent event) {
		try {
			this.profileService.friendship(this.authProfile, this.profileOwner);
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
	
}
