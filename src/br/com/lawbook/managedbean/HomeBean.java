package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.model.LazyDataModel;

import br.com.lawbook.business.PostService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 03NOV2011-08
 */
@ManagedBean
@SessionScoped
public class HomeBean implements Serializable {
	
	private Profile profile;
	private LazyDataModel<Post> stream;
	private ProfileService profileService;
	private PostService postService;
	private static final long serialVersionUID = 928727904018740163L;

	public HomeBean() {
		this.profileService = ProfileService.getInstance();
		this.postService = PostService.getInstance();
		try {
			this.setProfile(this.profileService.getAuthorizedUserProfile());
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.errorMessage("Authentication Error", "Problem with authentication process: " + e.getMessage());
		}
	}
	
	public LazyDataModel<Post> getStream() {
		return this.stream;
	}
	
	@PostConstruct
	public void loadLazyStream() {
		if (this.stream == null) {
			this.stream = new LazyDataModel<Post>() {

				private static final long serialVersionUID = 311055813797675620L;

				@Override
				public List<Post> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {
					List<Post> st = postService.getStream(profile, first, pageSize);
					return st;
				}
			};
			this.stream.setRowCount(this.postService.getPostsCount());
		}
	}

	public Profile getProfile() {
		return profile;
	}

	private void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public void removePost(ActionEvent event) {
		Post post = (Post) this.stream.getRowData();
		try {
			if (post.getSender().getId().equals(profile.getId())) {
				this.postService.delete(post);
				FacesUtil.infoMessage("=)", "Post deleted!");
			} else {
				FacesUtil.warnMessage("=|", "You cannot delete this post");
			}
		} catch (Exception e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
}
