package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import br.com.lawbook.business.ProcessService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Process;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 23NOV2011-01
 */
@ManagedBean
@SessionScoped
public class ProcessBean implements Serializable {

	private Profile authProfile;
	private List<Process> processes;
	private DataModel<Process> defProcesses;
	private DataModel<Process> resProcesses;
	private DataModel<Process> petProcesses;
	private static final ProfileService profileService = ProfileService.getInstance();
	private static final ProcessService processService = ProcessService.getInstance();
	private static final long serialVersionUID = 1823343553642147940L;
	private final static Logger LOG = Logger.getLogger("ProcessBean");

	public ProcessBean() {
		LOG.info("#### ProcessBean created");
		try {
			this.setAuthProfile(profileService.getAuthorizedUserProfile());
			this.processes = processService.getMyProcesses(authProfile);
			this.resProcesses = new ListDataModel<Process>();
		} catch (final Exception e) {
			LOG.severe(e.getMessage());
			FacesUtil.errorMessage("Authentication Error", "Problem with authentication process: " + e.getMessage());
		}
	}
	
	@PostConstruct
	public void loadProcessesTable() {
		// TODO load each process list according with user characteristic (petitioner, responsible, defendant)
		LOG.info("#### loadProcessesTable()");
	}

	public List<Process> getProcesses() {
		return this.processes;
	}

	public DataModel<Process> getDefProcesses() {
		return defProcesses;
	}

	public DataModel<Process> getResProcesses() {
		return resProcesses;
	}

	public DataModel<Process> getPetProcesses() {
		return petProcesses;
	}
	
	public Profile getAuthProfile() {
		return this.authProfile;
	}

	private void setAuthProfile(final Profile profile) {
		this.authProfile = profile;
	}

}
