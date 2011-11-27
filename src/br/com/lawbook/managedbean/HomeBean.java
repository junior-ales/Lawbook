package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.model.LazyDataModel;

import br.com.lawbook.business.service.PostService;
import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27NOV2011-10
 */
@ManagedBean
@SessionScoped
public class HomeBean implements Serializable {

	private Profile authProfile;
	private Profile publicProfile;
	private LazyDataModel<Post> stream;
	private static final Logger LOG = Logger.getLogger("HomeBean");
	private static final long serialVersionUID = -7732382195991593343L;
	private static final PostService POST_SERVICE = PostService.getInstance();
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();

	public HomeBean() {
		try {
			this.authProfile = PROFILE_SERVICE.getAuthorizedUserProfile();
		} catch (final Exception e) {
			LOG.severe(e.getMessage());
			FacesUtil.errorMessage("Authentication Error", "Problem with authentication process: " + e.getMessage());
		}
	}

	@PostConstruct
	public void loadLazyStream() {
		if (this.stream == null) {
			this.stream = new LazyDataModel<Post>() {

				private static final long serialVersionUID = 311055813797675620L;

				@Override
				public List<Post> load(final int first, final int pageSize, final String sortField, final boolean sortOrder, final Map<String, String> filters) {
					final List<Post> st = POST_SERVICE.getStream(HomeBean.this.authProfile, first, pageSize);
					return st;
				}
			};
			this.stream.setRowCount(POST_SERVICE.getPostsCount());
		}
	}

	public void removePost(final ActionEvent event) {
		final Post post = (Post) this.stream.getRowData();
		try {
			if (post.getSender().getId().equals(this.authProfile.getId())) {
				POST_SERVICE.delete(post);
				FacesUtil.infoMessage("=)", "Post deleted!");
			} else {
				FacesUtil.warnMessage("=|", "You cannot delete this post");
			}
		} catch (final Exception e) {
			LOG.severe(e.getMessage());
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public LazyDataModel<Post> getStream() {
		return this.stream;
	}

	public Profile getAuthProfile() {
		return this.authProfile;
	}

	public void setAuthProfile(final Profile profile) {
		this.authProfile = profile;
	}

	public Profile getPublicProfile() {
		if (this.publicProfile == null) this.publicProfile = PROFILE_SERVICE.getPublicProfile();
		return this.publicProfile;
	}

}
