package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
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

	private Date birth;
	private String rg;
	private String cpf;
	private String cnpj;
	private String phone;
	private Long profileId;
	private Profile customer;
	private static final long serialVersionUID = 8427046977090659562L;
	private static final Logger LOG = Logger.getLogger("CustomerBean");
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();

	public CustomerBean() {
		LOG.info("#### CustomerBean created");
		try {
			this.profileId = Long.valueOf(FacesUtil.getExternalContext().getRequestParameterMap().get("newUserProfileId"));
			this.customer = PROFILE_SERVICE.getProfileById(this.profileId);

			if (this.customer.getBirth() != null) this.birth = this.customer.getBirth().getTime();
			if (this.customer.getRg() != null) this.rg = this.customer.getRg().toString();
			if (this.customer.getCpf() != null) this.cpf = this.customer.getCpf().toString();
			if (this.customer.getCnpj() != null) this.cnpj = this.customer.getCnpj().toString();
			if (this.customer.getPhone() != null) this.phone = this.customer.getPhone().toString();

		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public void updateProfile() {
		LOG.info("#### updateProfile()");
		final NumberFormat nf = NumberFormat.getInstance();
		try {
			if(!this.rg.isEmpty()) this.customer.setRg((Integer) nf.parse(this.rg));
			if(!this.phone.isEmpty()) this.customer.setPhone((Integer) nf.parse(this.phone));
			if (!this.cpf.isEmpty() && !PROFILE_SERVICE.cpfValidation(this.cpf)) {
				FacesUtil.warnMessage("=|", "Invalid CPF");
				return;
			}
			if (!this.cnpj.isEmpty() && !PROFILE_SERVICE.cnpjValidation(this.cnpj)) {
				FacesUtil.warnMessage("=|", "Invalid CNPJ");
				return;
			}
			final Calendar auxDate = Calendar.getInstance();
			auxDate.setTime(this.birth);
			this.customer.setBirth(auxDate);

			PROFILE_SERVICE.update(this.customer);

			this.customer = new Profile();
			this.birth = new Date();
			this.rg = "";
			this.cpf = "";
			this.cnpj = "";
			this.phone = "";

			FacesUtil.infoMessage("=)", "Profile updated successfully");
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final ParseException e) {
			FacesUtil.warnMessage("=|", "Use only numers in RG and Phone field");
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

}
