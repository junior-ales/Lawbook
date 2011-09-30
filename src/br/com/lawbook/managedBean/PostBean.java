package br.com.lawbook.managedBean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.lawbook.business.PostService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 19SEP2011-02
 */
@ManagedBean
@RequestScoped
public class PostBean {

	private PostService service;
	private Long profileId = 99L;

	public PostBean() {
		this.service = PostService.getInstance();
	}
	
	public Long getProfileId() {
		return profileId;
	}
	
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	
	public List<Post> getStream(List<Profile> friendsList) {
		return service.getStream(friendsList);
	}
	
	public List<Post> getProfileWall() {
		return service.getWall(profileId);
	}
}
