package br.com.lawbook.managedBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
 * @version 18OCT2011-03
 */
@ManagedBean
@SessionScoped
public class ProfileBean {
	
	private LazyDataModel<Post> stream;
	private ProfileService service;
	private PostService postService = PostService.getInstance();
	private Profile profile;

	public ProfileBean() {
		this.service = ProfileService.getInstance();
		Profile profile = new Profile();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext){
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication){
            	String username = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
				profile = this.service.getProfileBy(username);
            }
        }
    	setProfile(profile);
	}
	
	@PostConstruct
	public void loadLazyStream() {
		if (this.stream == null) {
			this.service = ProfileService.getInstance();
			this.postService = PostService.getInstance();
			this.stream = new LazyDataModel<Post>() {
				private static final long serialVersionUID = 1L;

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
	
	public LazyDataModel<Post> getStream() {
		return this.stream;
	}
	
	public List<Post> getProfileWall() {
		PostService postService = PostService.getInstance();
		return postService.getWall(this.profile.getId());
	}

	public Profile getProfile() {
		return profile;
	}

	private void setProfile(Profile profile) {
		if (!this.service.checkIfExist(profile.getId())) {
			throw new IllegalArgumentException("Profile doesn't exist, please create one to use this feature");
		}
		this.profile = profile;
	}
	
}
