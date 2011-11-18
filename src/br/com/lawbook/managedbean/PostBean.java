package br.com.lawbook.managedbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.lawbook.business.PostService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 18NOV2011-06
 */
@ManagedBean
@RequestScoped
public class PostBean {
	
	private Post post;
	private String postContent;
	private Profile receiver;

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public Profile getReceiver() {
		return receiver;
	}

	public void setReceiver(Profile receiver) {
		this.receiver = receiver;
	}

	public String savePost() {
		if (this.postContent.trim().equals("")) {
			FacesUtil.warnMessage("=|", "Post must contain some text");
			return "";
		}
		this.post = new Post();
		try {
			this.post.setContent(this.postContent);
			this.post.setSender(ProfileService.getInstance().getAuthorizedUserProfile());
			this.post.setReceiver(this.receiver);
			PostService.getInstance().create(post);
			FacesUtil.infoMessage("=)", postContent);
			this.postContent = null;
		} catch (Exception e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
		return "";
	}

}
