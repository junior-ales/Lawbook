package br.com.lawbook.managedbean;

import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.lawbook.business.service.PostService;
import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 01DEC2011-08
 */
@ManagedBean
@RequestScoped
public class PostBean {

	private Post post;
	private Profile receiver;
	private String postContent;
	private final ResourceBundle rs;
	private static final Logger LOG = Logger.getLogger("br.com.lawbook.managedbean");
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();

	public PostBean() {
		this.rs = ResourceBundle.getBundle("br.com.lawbook.util.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		LOG.info(this.getClass().getSimpleName() + ": ManagedBean Created" );
	}

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
			FacesUtil.warnMessage("=|", this.rs.getString("msg_noContentPost"));
			return "";
		}
		this.post = new Post();
		try {
			this.post.setContent(this.postContent);
			this.post.setSender(PROFILE_SERVICE.getAuthorizedUserProfile());
			this.post.setReceiver(this.receiver);
			this.post.setDateTime(Calendar.getInstance());
			PostService.getInstance().create(this.post);
			FacesUtil.infoMessage("=)", this.rs.getString("msg_postSuccess"));
			this.postContent = null;
		} catch (final Exception e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.errorMessage("=(", e.getMessage());
		}
		return "";
	}

}
