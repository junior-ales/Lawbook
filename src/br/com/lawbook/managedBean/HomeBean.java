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
 * @version 23OCT2011-02
 */
@ManagedBean
@ViewScoped
public class HomeBean implements Serializable {
	
	private Profile profile;
	private LazyDataModel<Post> stream;
	private ProfileService service;
	private PostService postService;
	private static final long serialVersionUID = 928727904018740163L;

	public HomeBean() {
		this.service = ProfileService.getInstance();
		Profile profile = new Profile();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext){
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication){
            	String username = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
				profile = getUserProfile(username);
            }
        }
    	setProfile(profile);
	}
	
	private Profile getUserProfile(String username) {
		return this.service.getProfileBy(username);
	}

	@PostConstruct
	public void loadLazyStream() {
		if (this.stream == null) {
			this.service = ProfileService.getInstance();
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
	
	public LazyDataModel<Post> getStream() {
		return this.stream;
	}

	public Profile getProfile() {
		return profile;
	}

	private void setProfile(Profile profile) {
		this.profile = profile;
	}
	
}
