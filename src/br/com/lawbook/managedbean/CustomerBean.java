package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.HibernateException;

import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 25NOV2011-02
 *
 */
@ManagedBean
@ViewScoped
public class CustomerBean implements Serializable {

	private Profile customer;
	private Long profileId;
	private String birth;
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();
	private static final Logger LOG = Logger.getLogger("CustomerBean");
	private static final long serialVersionUID = -3626180881761384330L;

	public CustomerBean() {
		LOG.info("#### CustomerBean created");
		try {
			this.profileId = Long.valueOf(FacesUtil.getExternalContext().getRequestParameterMap().get("newUserProfileId"));
			this.customer = PROFILE_SERVICE.getProfileById(this.profileId);
			if (this.customer.getBirth() != null) {
				final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				this.birth = df.format(this.customer.getBirth().getTime());
			}
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public void updateProfile() {
		LOG.info("#### updateProfile()");
		try {
			this.customer.setBirth(getDate(this.birth));
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

	public String getBirth() {
		return this.birth;
	}

	public void setBirth(final String birth) {
		this.birth = birth;
	}

	public Profile getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Profile customer) {
		this.customer = customer;
	}

	private static Calendar getDate(final String dateString) {
		final Calendar c = Calendar.getInstance();
		final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			c.setTime(df.parse(dateString));
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return c;
	}

}
