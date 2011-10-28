package br.com.lawbook.managedBean;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.lawbook.business.PostService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27OCT2011-04
 */
@ManagedBean
@SessionScoped
public class HomeBean implements Serializable {
	
	private Profile profile;
	private Profile public_;
	private Post post;
	private LazyDataModel<Post> stream;
	private ProfileService profileService;
	private PostService postService;
	private static final long serialVersionUID = 928727904018740163L;

	public HomeBean() {
		this.profileService = ProfileService.getInstance();
		this.postService = PostService.getInstance();
		this.post = new Post();
		this.public_ = this.profileService.getProfileByUserName("public");
		Profile profile = new Profile();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext){
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication){
            	String username = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
				profile = this.profileService.getProfileByUserName(username);
            }
        }
    	setProfile(profile);
	}
	
	public LazyDataModel<Post> getStream() {
		return this.stream;
	}
	
	@PostConstruct
	public void loadLazyStream() {
		if (this.stream == null) {
			this.profileService = ProfileService.getInstance();
			this.postService = PostService.getInstance();
			this.stream = new LazyDataModel<Post>() {
				private static final long serialVersionUID = -4238038748234463347L;

				@Override
				public List<Post> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {
					HashMap<String, Object> attributes = new HashMap<String, Object>();
					attributes.put("profile", profile);
					attributes.put("first", new Integer(first));
					attributes.put("pageSize", new Integer(pageSize));
					List<Post> posts = postService.getStream(attributes);
					return posts;
				}
			};
			this.stream.setRowCount(this.postService.getPostsCount());
		}
	}
	
	public void savePost(ActionEvent event) {
		this.post.setDateTime(Calendar.getInstance());
		this.post.setSender(this.profile);
		this.post.setReceiver(this.public_);
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
