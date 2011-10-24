package br.com.lawbook.managedBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.lawbook.business.PostService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 19OCT2011-04
 */
@ManagedBean
@ViewScoped
public class ProfileBean implements Serializable {
	
	private Profile profile;
	private LazyDataModel<Post> wall;
	private ProfileService service;
	private PostService postService;
	private static final long serialVersionUID = 5494870990228898461L;

	public ProfileBean() {
		this.service = ProfileService.getInstance();
		Profile profile = new Profile();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext){
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication){
            	String username = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
				profile = this.service.getProfileByUserName(username);
            }
        }
    	setProfile(profile);
	}
	
	@PostConstruct
	public void loadLazyWall() {
		if (this.wall == null) {
			this.service = ProfileService.getInstance();
			this.postService = PostService.getInstance();
			this.wall = new LazyDataModel<Post>() {
				private static final long serialVersionUID = -4238038748234463347L;

				@Override
				public List<Post> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {
					HashMap<String, Object> attributes = new HashMap<String, Object>();
					attributes.put("profileId", profile.getId());
					attributes.put("first", new Integer(first));
					attributes.put("pageSize", new Integer(pageSize));
					List<Post> posts = postService.getWall(attributes);
					return posts;
				}
			};
			this.wall.setRowCount(this.postService.getPostsCount());
		}
	}
	
	public LazyDataModel<Post> getWall() {
		return this.wall;
	}

	public Profile getProfile() {
		return profile;
	}

	private void setProfile(Profile profile) {
		this.profile = profile;
	}
	
}
