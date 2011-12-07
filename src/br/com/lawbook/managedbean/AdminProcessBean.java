package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.primefaces.event.SelectEvent;

import br.com.lawbook.business.converter.UserConverter;
import br.com.lawbook.business.service.ProcessService;
import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Process;
import br.com.lawbook.model.Profile;
import br.com.lawbook.model.User;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 07DEC2011-07
 *
 */
@ManagedBean
@ViewScoped
public class AdminProcessBean implements Serializable {

	private User part;
	private Process process;
	private Date openingDate;
	private final List<User> users;
	private final Profile authProfile;
	private transient final ResourceBundle rs;
	private static final long serialVersionUID = -7422431632550978387L;
	private static final Logger LOG = Logger.getLogger("br.com.lawbook.managedbean");
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();
	private static final ProcessService PROCESS_SERVICE = ProcessService.getInstance();

	public AdminProcessBean() {
		this.rs = ResourceBundle.getBundle("br.com.lawbook.util.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		this.process = new Process();
		this.part = new User();
		this.authProfile = PROFILE_SERVICE.getAuthorizedUserProfile();
		this.users = UserConverter.users;
		LOG.info(this.getClass().getSimpleName() + ": Users loaded");
		final String mode = FacesUtil.getExternalContext().getRequestParameterMap().get("mode");
		try {
			if (mode.equals("edit")) {
				final Long processId = Long.valueOf(FacesUtil.getExternalContext().getRequestParameterMap().get("processId"));
				this.process = PROCESS_SERVICE.getById(processId);
				this.openingDate = this.process.getOpeningDate().getTime();
			}
			else if (!mode.equals("new")) {
				LOG.severe(this.getClass().getSimpleName() + ": Error on constructor. Invalid process bean mode.");
				FacesUtil.errorMessage("=(", this.rs.getString("msg_reqParam"));
			}
		} catch (final IllegalArgumentException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.errorMessage("=(", e.getMessage());
		}
		LOG.info(this.getClass().getSimpleName() + ": ManagedBean Created" );
	}

	public void saveProcess() {
		LOG.info(this.getClass().getSimpleName() + ": saveProcess()");
		try {
			final Calendar auxDate = Calendar.getInstance();
			auxDate.setTime(this.openingDate);
			this.process.setOpeningDate(auxDate);
			PROCESS_SERVICE.create(this.process);
			this.process = new Process();
			this.part = new User();
			FacesUtil.infoMessage("=)", this.rs.getString("msg_process_saved"));
		} catch (final IllegalArgumentException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public void updateProcess() {
		LOG.info(this.getClass().getSimpleName() + ": updateProcess()");
		try {
			final Calendar auxDate = Calendar.getInstance();
			auxDate.setTime(this.openingDate);
			this.process.setOpeningDate(auxDate);
			PROCESS_SERVICE.update(this.process);
			this.process = new Process();
			this.part = new User();
			FacesUtil.infoMessage("=)", this.rs.getString("msg_process_updated"));
		} catch (final IllegalArgumentException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			LOG.severe(this.getClass().getSimpleName() + ": "+ e.getMessage());
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public List<User> completeUsers(String query) {
		LOG.info(this.getClass().getSimpleName() + ": completeUsers(String query)");
		if (query == null) query = "";
        final List<User> results = new ArrayList<User>();
        for (final User u : this.users) {
            if (u.getUserName().startsWith(query)) {
                results.add(u);
            }
        }
        return results;
    }

	public void handleSelect(final SelectEvent event) {
		LOG.info(this.getClass().getSimpleName() + ": handleSelect(final SelectEvent event) ");
		final Profile p = PROFILE_SERVICE.getProfileByUserName(this.part.getUserName());
		if (event.getComponent().getId().equals("responsibleName")) {
			this.process.setResponsible(p);
			LOG.info(this.getClass().getSimpleName() + ": responsibleName set");
		} else if(event.getComponent().getId().equals("petitionerName")) {
			this.process.setPetitioner(p);
			LOG.info(this.getClass().getSimpleName() + ": petitionerName set");
		} else {
			this.process.setDefendant(p);
			LOG.info(this.getClass().getSimpleName() + ": defendantName set");
		}

	}

	public Profile getAuthProfile() {
		return this.authProfile;
	}

	public User getPart() {
		return this.part;
	}

	public void setPart(final User part) {
		this.part = part;
	}

	public Process getProcess() {
		return this.process;
	}

	public void setProcess(final Process process) {
		this.process = process;
	}

	public Date getOpeningDate() {
		return this.openingDate;
	}

	public void setOpeningDate(final Date openingDate) {
		this.openingDate = openingDate;
	}

}
