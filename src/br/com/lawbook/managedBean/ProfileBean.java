package br.com.lawbook.managedBean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.lawbook.business.PostService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 06OCT2011-04
 */
@ManagedBean
@SessionScoped
public class ProfileBean {
	
	private ProfileService service;
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
	
	public List<Post> getStream() {
		PostService postService = PostService.getInstance();
		return postService.getStream(this.profile.getFriendsList());
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
