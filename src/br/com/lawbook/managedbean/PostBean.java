package br.com.lawbook.managedbean;

import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.lawbook.business.service.PostService;
import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27NOV2011-07
 */
@ManagedBean
@RequestScoped
public class PostBean {

	private Post post;
	private String postContent;
	private Profile receiver;
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();

	public String getPostContent() {
		return this.postContent;
	}

	public void setPostContent(final String postContent) {
		this.postContent = postContent;
	}

	public Profile getReceiver() {
		return this.receiver;
	}

	public void setReceiver(final Profile receiver) {
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
			this.post.setSender(PROFILE_SERVICE.getAuthorizedUserProfile());
			this.post.setReceiver(this.receiver);
			this.post.setDateTime(Calendar.getInstance());
			PostService.getInstance().create(this.post);
			FacesUtil.infoMessage("=)", this.postContent);
			this.postContent = null;
		} catch (final Exception e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
		return "";
	}

}
