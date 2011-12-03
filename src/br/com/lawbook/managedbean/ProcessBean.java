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
 * @version 01DEC2011-04
 *
 */
@ManagedBean
@ViewScoped
public class ProcessBean implements Serializable {

	private static final long serialVersionUID = -4302328893337520681L;
	private User part;
	private Process process;
	private Date openingDate;
	private List<User> users;
	private Profile authProfile;
	private List<Process> processes;
	private final ResourceBundle rs;
	private static final Logger LOG = Logger.getLogger("br.com.lawbook.managedbean");
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();
	private static final ProcessService PROCESS_SERVICE = ProcessService.getInstance();

	public ProcessBean() {
		this.rs = ResourceBundle.getBundle("br.com.lawbook.util.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		this.process = new Process();
		if (FacesUtil.getExternalContext().getRequestServletPath().contains("admin")) { // testing if is an administration page
			this.users = UserConverter.users;
			LOG.info(this.getClass().getSimpleName() + ": Users loaded");
		}
		try {
			this.authProfile = PROFILE_SERVICE.getAuthorizedUserProfile();
			if (FacesUtil.getExternalContext().getRequestServletPath().contains("processes")) {
				this.processes = PROCESS_SERVICE.getAll();
				LOG.info(this.getClass().getSimpleName() + ": All processes loaded");
			} else {
				this.processes = PROCESS_SERVICE.getMyProcesses(this.authProfile);
				LOG.info(this.getClass().getSimpleName() + ": My processes loaded");
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
	
	public String editProcess() {
		String outcome = "";
		LOG.warning("Edit Process unimplemented yet");
		FacesUtil.warnMessage("=|", "Edit Process unimplemented yet");
		return outcome;
	}

	public List<User> completeUsers(String query) {
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

	public List<Process> getProcesses() {
		return this.processes;
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
