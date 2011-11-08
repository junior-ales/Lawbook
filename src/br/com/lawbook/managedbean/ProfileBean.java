package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.LazyDataModel;

import br.com.lawbook.business.PostService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 02NOV2011-06
 */
@ManagedBean
@SessionScoped
public class ProfileBean implements Serializable {
	
	private Profile profile;
	private LazyDataModel<Post> wall;
	private ProfileService profileService;
	private PostService postService;
	private static final long serialVersionUID = 5494870990228898461L;

	public ProfileBean() {
		this.profileService = ProfileService.getInstance();
		try {
			setProfile(this.profileService.getAuthorizedUserProfile());
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
					List<Post> posts = postService.getWall(profile, first, pageSize);
					return posts;
				}
			};
			this.wall.setRowCount(this.postService.getPostsCount());
		}
	}
	
	public LazyDataModel<Post> getWall() {
		return this.wall;
	}
	
	public String removePost() {
		Post post = (Post) this.wall.getRowData();
		try {
			this.postService.delete(post);
			FacesUtil.infoMessage("=)", "Post deleted!");
		} catch (Exception e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
		return "";
	}

	public Profile getProfile() {
		return profile;
	}

	private void setProfile(Profile profile) {
		this.profile = profile;
	}
	
}
