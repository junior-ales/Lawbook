package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
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
 * @version 28OCT2011-05
 */
@ManagedBean
@SessionScoped
public class HomeBean implements Serializable {
	
	private Profile profile;
	private final transient Profile publicProfile; 
	private Post post;
	private transient LazyDataModel<Post> stream;
	private final transient ProfileService profileService;
	private final transient PostService postService;
	private static final long serialVersionUID = 928727904018740163L;

	public HomeBean() {
		this.profileService = ProfileService.getInstance();
		this.postService = PostService.getInstance();
		this.post = new Post();
		this.publicProfile = this.profileService.getProfileByUserName("public");
		try {
			setProfile(this.profileService.getAuthorizedUserProfile());
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
				private static final long serialVersionUID = -4238038748234463347L;

				@Override
				public List<Post> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {
					final Map<String, Object> attributes = new HashMap<String, Object>();
					attributes.put("profile", profile);
					attributes.put("first", Integer.valueOf(first));
					attributes.put("pageSize", Integer.valueOf(pageSize));
					return postService.getStream(attributes);
				}
			};
			this.stream.setRowCount(this.postService.getPostsCount());
		}
	}
	
	public void savePost(ActionEvent event) {
		this.post.setDateTime(Calendar.getInstance());
		this.post.setSender(this.profile);
		this.post.setReceiver(this.publicProfile);
		this.postService.save(this.post);
		FacesUtil.successMessage("", "Posted");
	}
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Profile getProfile() {
		return profile;
	}

	private void setProfile(Profile profile) {
		this.profile = profile;
	}
	
}
