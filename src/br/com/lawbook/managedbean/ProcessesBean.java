package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

import br.com.lawbook.business.service.ProcessService;
import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Process;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 05DEC2011-07
 *
 */
@ManagedBean
@SessionScoped
public class ProcessesBean implements Serializable{

	private Process process;
	private final Profile authProfile;
	private final ResourceBundle rs;
	private LazyDataModel<Process> processes;
	private static final long serialVersionUID = 5306181199550735654L;
	private static final Logger LOG = Logger.getLogger("br.com.lawbook.managedbean");
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();
	private static final ProcessService PROCESS_SERVICE = ProcessService.getInstance();

	public ProcessesBean() {
		this.rs = ResourceBundle.getBundle("br.com.lawbook.util.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		this.process = new Process();
		this.authProfile = PROFILE_SERVICE.getAuthorizedUserProfile();
		LOG.info(this.getClass().getSimpleName() + ": ManagedBean Created" );
	}

	@PostConstruct
	public void loadProcesses() {
		LOG.info(this.getClass().getSimpleName() + ": loadProcesses()" );
		
		if (this.processes == null) {
			this.processes = new LazyDataModel<Process>() {
				private static final long serialVersionUID = 6602869058744041528L;

				@Override
				public List<Process> load(final int first, final int pageSize, final String sortField, final boolean sortOrder, final Map<String, String> filters) {
					final List<Process> processes = PROCESS_SERVICE.getMyProcesses(ProcessesBean.this.authProfile, first, pageSize);
					LOG.info(this.getClass().getSimpleName() + ": My processes loaded" );
					return processes;
				}
			};
			this.processes.setRowCount(PROCESS_SERVICE.getProcessCount());
		}
	}

	public String editProcess() {
		LOG.info(this.getClass().getSimpleName() + ": editProcess()");
		String outcome = "";
		if (this.process.getId() == null) {
			LOG.warning("Occured a problem in process edition, process maybe isn't being set correctly");
			FacesUtil.warnMessage("=|", rs.getString("msg_processProblem"));
		} else {
			outcome = "editProcess?processId=" + this.process.getId() + "&mode=edit&faces-redirect=true";
		}
		return outcome;
	}

	public LazyDataModel<Process> getProcesses() {
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
