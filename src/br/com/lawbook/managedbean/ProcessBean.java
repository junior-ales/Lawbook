package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
 * @version 26NOV2011-02
 *
 */
@ManagedBean
@ViewScoped
public class ProcessBean implements Serializable {

	private User part;
	private Process process;
	private Profile authProfile;
	private Date openingDate;
	private List<Process> processes;
	private List<User> users;
	private static final ProfileService profileService = ProfileService.getInstance();
	private static final ProcessService processService = ProcessService.getInstance();
	private final static Logger LOG = Logger.getLogger("ProcessBean");
	private static final long serialVersionUID = -7499567752071913343L;

	// TODO ask someone legal areas and situations to add in drop-down lists in newProcess.xhtml

	public ProcessBean() {
		LOG.info("#### ProcessBean created");
		this.process = new Process();
		try {
			this.setAuthProfile(profileService.getAuthorizedUserProfile());
			if (FacesUtil.getExternalContext().getRequestServletPath().contains("admin")) { // testing if is an administration page
				this.users = UserConverter.users;
				LOG.info("#### Users loaded");
			} else {
				this.processes = processService.getMyProcesses(this.authProfile);
				LOG.info("#### Users not loaded");
			}
		} catch (final Exception e) {
			LOG.severe(e.getMessage());
			FacesUtil.errorMessage("Authentication Error", "Problem with authentication process: " + e.getMessage());
		}
	}

	public void saveProcess() {
		try {
			Calendar auxDate = Calendar.getInstance();
			auxDate.setTime(this.openingDate); 
			this.process.setOpeningDate(auxDate);
			processService.create(this.process);
			FacesUtil.infoMessage("=)", "Process saved succesfully");
		} catch (final IllegalArgumentException e) {
			LOG.severe(e.getMessage());
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			LOG.severe(e.getMessage());
			FacesUtil.errorMessage("=(", e.getMessage());
		} finally {
			this.process = new Process();
			this.part = new User();
		}
	}

	public List<User> completeUsers(String query) {
		if (query == null) query = "";
        List<User> results = new ArrayList<User>();
        for (final User u : this.users) {
            if (u.getUserName().startsWith(query)) {
                results.add(u);
            }
        }
        return results;
    }

	public void handleSelect(final SelectEvent event) {
		LOG.info("#### handleSelect(final SelectEvent event) ");
		final Profile p = profileService.getProfileByUserName(this.part.getUserName());
		if (event.getComponent().getId().equals("responsibleName")) {
			this.process.setResponsible(p);
			LOG.info("#### responsibleName set");
		} else if(event.getComponent().getId().equals("petitionerName")) {
			this.process.setPetitioner(p);
			LOG.info("#### petitionerName set");
		} else {
			this.process.setDefendant(p);
			LOG.info("#### defendantName set");
		}

	}

	public List<Process> getProcesses() {
		return this.processes;
	}

	public Profile getAuthProfile() {
		return this.authProfile;
	}

	private void setAuthProfile(final Profile profile) {
		this.authProfile = profile;
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
