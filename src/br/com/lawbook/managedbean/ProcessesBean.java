package br.com.lawbook.managedbean;

import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;

import org.hibernate.HibernateException;

import br.com.lawbook.business.service.ProcessService;
import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Process;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 03DEC2011-05
 *
 */
@ManagedBean
@RequestScoped
public class ProcessesBean {

	private Process process;
	private Profile authProfile;
	private final ResourceBundle rs;
	private ListDataModel<Process> processes;
	private static final Logger LOG = Logger.getLogger("br.com.lawbook.managedbean");
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();
	private static final ProcessService PROCESS_SERVICE = ProcessService.getInstance();

	public ProcessesBean() {
		this.rs = ResourceBundle.getBundle("br.com.lawbook.util.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		this.process = new Process();
		this.authProfile = PROFILE_SERVICE.getAuthorizedUserProfile();
		load();
		LOG.info(this.getClass().getSimpleName() + ": ManagedBean Created" );
	}

	private void load() {
		LOG.info(this.getClass().getSimpleName() + ": load()" );
		String mode = FacesUtil.getExternalContext().getRequestParameterMap().get("mode");
		LOG.info(this.getClass().getSimpleName() + ": mode = " + mode);
		try {
			if (mode.equals("list")) {  // list all authorized user related processes
				this.processes = new ListDataModel<Process>(PROCESS_SERVICE.getMyProcesses(this.authProfile));
			}
			else if (mode.equals("choose")) { // list all process to choose what is going to be edited
				this.processes = new ListDataModel<Process>(PROCESS_SERVICE.getAll()); 
			}
			else {
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
	}
	
	public void prepareEditProcess(final ActionEvent event) {
		LOG.info(this.getClass().getSimpleName() + ": prepareEditProcess(ActionEvent event)");
		this.process = (Process) this.processes.getRowData();
	}

	public String editProcess() {
		LOG.info(this.getClass().getSimpleName() + ": editProcess()");
		String outcome = "";
		if (this.process.getId() == null) {
			LOG.warning("Occured a problem in process edition, process maybe isn't being set correctly");
			FacesUtil.warnMessage("=|", "Occured a problem in process edition");
		} else {
			outcome = "editProcess?processId=" + this.process.getId() + "&mode=edit&faces-redirect=true";
		}
		return outcome;
	}

	public ListDataModel<Process> getProcesses() {
		return this.processes;
	}

	public Profile getAuthProfile() {
		return this.authProfile;
	}

	public Process getProcess() {
		return this.process;
	}

	public void setProcess(final Process process) {
		this.process = process;
	}

}
