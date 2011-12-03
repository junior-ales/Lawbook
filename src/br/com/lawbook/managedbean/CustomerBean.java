package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;

import br.com.lawbook.business.service.LocationService;
import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Location;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 01DEC2011-05
 *
 */
@ManagedBean
@ViewScoped
public class CustomerBean implements Serializable {

	private static final long serialVersionUID = 8168538165063080055L;
	private Date birth;
	private String rg;
	private String cpf;
	private String cnpj;
	private String phone;
	private Long profileId;
	private Location local;
	private Profile customer;
	private static final Logger LOG = Logger.getLogger("br.com.lawbook.managedbean");
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();
	private static final LocationService LOCATION_SERVICE = LocationService.getInstance();
	private ResourceBundle rs;

	public CustomerBean() {
		this.rs = ResourceBundle.getBundle("br.com.lawbook.util.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		try {
			this.profileId = Long.valueOf(FacesUtil.getExternalContext().getRequestParameterMap().get("newUserProfileId"));
			this.customer = PROFILE_SERVICE.getProfileById(this.profileId);
			this.birth = new Date();
			this.local = new Location();

			if (this.customer.getBirth() != null) this.birth = this.customer.getBirth().getTime();
			if (this.customer.getRg() != null) this.rg = this.customer.getRg().toString();
			if (this.customer.getCpf() != null) this.cpf = this.customer.getCpf().toString();
			if (this.customer.getCnpj() != null) this.cnpj = this.customer.getCnpj().toString();
			if (this.customer.getPhone() != null) this.phone = this.customer.getPhone().toString();
			if (this.customer.getLocation() != null) this.local = this.customer.getLocation();

		} catch (final IllegalArgumentException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.errorMessage("=(", e.getMessage());
		}
		LOG.info(this.getClass().getSimpleName() + ": ManagedBean created");
	}

	public void updateProfile() {
		LOG.info(this.getClass().getSimpleName() + ": updateProfile()");
		try {
			if (!this.rg.isEmpty()) {
				this.customer.setRg(Long.valueOf(this.rg));
				LOG.info(this.getClass().getSimpleName() + ": updateProfile(): rg");
			}

			if (!this.phone.isEmpty()) {
				this.phone = this.phone.replaceAll("[^0-9]", "");
				this.customer.setPhone(Long.valueOf(this.phone));
				LOG.info(this.getClass().getSimpleName() + ": updateProfile(): phone");
			}

			if (!this.cpf.isEmpty()) {
				this.cpf = this.cpf.replaceAll("[^0-9]", "");
				if (!PROFILE_SERVICE.cpfValidation(this.cpf)) {
					FacesUtil.warnMessage("=|", this.rs.getString("msg_invalidCPF"));
					return;
				}
				this.customer.setCpf(Long.valueOf(this.cpf));
				LOG.info(this.getClass().getSimpleName() + ": updateProfile(): cpf");
			}
			if (!this.cnpj.isEmpty()) {
				this.cnpj = this.cnpj.replaceAll("[^0-9]", "");
				if (!PROFILE_SERVICE.cnpjValidation(this.cnpj)) {
					FacesUtil.warnMessage("=|", this.rs.getString("msg_invalidCNPJ"));
					return;
				}
				this.customer.setCnpj(Long.valueOf(this.cnpj));
				LOG.info(this.getClass().getSimpleName() + ": updateProfile(): cnpj");
			}

			if (this.local.getId() == null) {
				LOCATION_SERVICE.save(this.local);
				LOG.info(this.getClass().getSimpleName() + ": updateProfile(): Location saved successfully");
			} else {
				LOCATION_SERVICE.update(this.local);
				LOG.info(this.getClass().getSimpleName() + ": updateProfile(): Location updated successfully");
			}
			this.customer.setLocation(this.local);
			LOG.info(this.getClass().getSimpleName() + ": updateProfile(): local");

			final Calendar auxDate = Calendar.getInstance();
			auxDate.setTime(this.birth);
			this.customer.setBirth(auxDate);
			LOG.info(this.getClass().getSimpleName() + ": updateProfile(): birth");

			PROFILE_SERVICE.update(this.customer);

			this.customer = new Profile();
			this.local = new Location();
			this.birth = new Date();
			this.rg = "";
			this.cpf = "";
			this.cnpj = "";
			this.phone = "";

			LOG.info(this.getClass().getSimpleName() + ": updateProfile(): Profile updated successfully");
			this.rs = ResourceBundle.getBundle("br.com.lawbook.util.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
			FacesUtil.infoMessage("=)", this.rs.getString("msg_profileUpdatedSuccess"));
		} catch (final NumberFormatException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final IllegalArgumentException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
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

	public String getRg() {
		return this.rg;
	}

	public void setRg(final String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(final String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(final String cnpj) {
		this.cnpj = cnpj;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public Location getLocal() {
		return this.local;
	}

	public void setLocal(final Location local) {
		this.local = local;
	}

}
