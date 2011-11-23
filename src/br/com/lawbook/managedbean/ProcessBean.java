package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.List;

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
	private DataModel<Process> processes;
	private static final ProfileService profileService = ProfileService.getInstance();
	private static final ProcessService processService = ProcessService.getInstance();
	private static final long serialVersionUID = 6260443686026229788L;

	public ProcessBean() {
		try {
			this.setAuthProfile(profileService.getAuthorizedUserProfile());
		} catch (final Exception e) {
			FacesUtil.errorMessage("Authentication Error", "Problem with authentication process: " + e.getMessage());
		}
	}

	public DataModel<Process> getProcesses() {
		List<Process> processes = processService.getMyProcesses(authProfile);
		this.processes = new ListDataModel<Process>(processes); 
		return this.processes;
	}

	public Profile getAuthProfile() {
		return this.authProfile;
	}

	private void setAuthProfile(final Profile profile) {
		this.authProfile = profile;
	}

}
