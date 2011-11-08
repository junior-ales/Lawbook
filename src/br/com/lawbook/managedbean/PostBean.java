package br.com.lawbook.managedbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import br.com.lawbook.business.PostService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 02NOV2011-05
 */
@ManagedBean
@RequestScoped
public class PostBean {
	
	private Post post;
	private String postContent;

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public void savePost(ActionEvent event) {
		if (this.postContent.trim().equals("")) {
			FacesUtil.warnMessage("=|", "Post must contain some text");
			return;
		}
		this.post = new Post();
		try {
			this.post.setContent(this.postContent);
			this.post.setSender(ProfileService.getInstance().getAuthorizedUserProfile());
			this.post.setReceiver(ProfileService.getInstance().getPublicProfile());
			PostService.getInstance().create(post);
			FacesUtil.infoMessage("=)", postContent);
			this.postContent = null;
		} catch (Exception e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

}
