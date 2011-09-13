package br.com.lawbook.managedBean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 13SEP2011-01
 */
@ManagedBean
@RequestScoped
public class ProfileBean {
	
	private ProfileService service;
	private Profile profile;

	public ProfileBean(Profile profile) {
		this.service = ProfileService.getInstance();
		setProfile(profile);
	}
	
	private List<Post> getStream() {
		List<Post> posts = service.getStream(this.profile);
		return posts;
	}

	private void setProfile(Profile profile) {
		
		this.profile = profile;
	}
	
}
