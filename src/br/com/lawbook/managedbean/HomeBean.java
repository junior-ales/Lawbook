package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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

	private static final long serialVersionUID = 2357208428945366980L;
	private final ResourceBundle rs;
	private Profile authProfile;
	private Profile publicProfile;
	private LazyDataModel<Post> stream;
	private static final Logger LOG = Logger.getLogger("br.com.lawbook.managedbean");
	private static final PostService POST_SERVICE = PostService.getInstance();
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();

	public HomeBean() {
		this.rs = ResourceBundle.getBundle("br.com.lawbook.util.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		try {
			this.publicProfile = PROFILE_SERVICE.getPublicProfile();
			this.authProfile = PROFILE_SERVICE.getAuthorizedUserProfile();
		} catch (final Exception e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.errorMessage("Authentication Error", "Problem with authentication process: " + e.getMessage());
		}
		LOG.info(this.getClass().getSimpleName() + ": ManagedBean Created" );
	}

	@PostConstruct
	public void loadLazyStream() {
		LOG.info(this.getClass().getSimpleName() + ": loadLazyStream()");
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
		LOG.info(this.getClass().getSimpleName() + ": removePost(ActionEvent event)");
		final Post post = (Post) this.stream.getRowData();
		try {
			if (post.getSender().getId().equals(this.authProfile.getId())) {
				POST_SERVICE.delete(post);
				FacesUtil.infoMessage("=)", this.rs.getString("msg_deleted"));
			} else {
				FacesUtil.warnMessage("=|", this.rs.getString("msg_notDeletable"));
			}
		} catch (final Exception e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public LazyDataModel<Post> getStream() {
		return this.stream;
	}

	public Profile getAuthProfile() {
		return this.authProfile;
	}

	public Profile getPublicProfile() {
		return this.publicProfile;
	}

}
