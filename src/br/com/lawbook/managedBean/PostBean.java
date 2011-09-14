package br.com.lawbook.managedBean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.lawbook.business.PostService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 14SEP2011-01
 */
@ManagedBean
@RequestScoped
public class PostBean {

	private PostService service;

	private PostBean() {
		this.service = PostService.getInstance();
	}
	
	public List<Post> getStream(List<Profile> friendsList) {
		return service.getStream(friendsList);
	}
	
	public List<Post> getWall(Long profileId) {
		return service.getWall(profileId);
	}
}
