package br.com.lawbook.managedBean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 14SEP2011-02
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
	
	public List<Post> getStream() {
		return service.getStream(this.profile);
	}

	private void setProfile(Profile profile) {
		if (!this.service.checkIfExist(profile.getId())) {
			throw new IllegalArgumentException("Profile doesn't exist, please create one to use this feature");
		}
		this.profile = profile;
	}
	
}
