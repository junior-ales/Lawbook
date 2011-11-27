package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.HibernateException;

import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27NOV2011-03
 *
 */
@ManagedBean
@ViewScoped
public class CustomerBean implements Serializable {

	private Profile customer;
	private Long profileId;
	private Date birth;
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();
	private static final Logger LOG = Logger.getLogger("CustomerBean");
	private static final long serialVersionUID = -3626180881761384330L;

	public CustomerBean() {
		LOG.info("#### CustomerBean created");
		try {
			this.profileId = Long.valueOf(FacesUtil.getExternalContext().getRequestParameterMap().get("newUserProfileId"));
			this.customer = PROFILE_SERVICE.getProfileById(this.profileId);
			if (this.customer.getBirth() != null) {
				this.birth = this.customer.getBirth().getTime();
			}
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public void updateProfile() {
		LOG.info("#### updateProfile()");
		final Calendar auxDate = Calendar.getInstance();
		auxDate.setTime(this.birth);
		this.customer.setBirth(auxDate);
		try {
			PROFILE_SERVICE.update(this.customer);
			FacesUtil.infoMessage("=)", "Profile updated successfully");
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public Long getProfileId() {
		return this.profileId;
	}

	public Date getBirth() {
		return this.birth;
	}

	public void setBirth(final Date birth) {
		this.birth = birth;
	}

	public Profile getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Profile customer) {
		this.customer = customer;
	}

}
