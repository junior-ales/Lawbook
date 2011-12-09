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
import javax.faces.event.ActionEvent;

import org.primefaces.model.LazyDataModel;

import br.com.lawbook.business.service.ProcessService;
import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Process;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 09DEC2011-02
 *
 */
@ManagedBean
@SessionScoped
public class AdminProcessesBean implements Serializable{

	private Long processId;
	private transient ResourceBundle rs;
	private final Profile authProfile;
	private LazyDataModel<Process> processes;
	private static final long serialVersionUID = 8389996789724191248L;
	private static final Logger LOG = Logger.getLogger("br.com.lawbook.managedbean");
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();
	private static final ProcessService PROCESS_SERVICE = ProcessService.getInstance();

	public AdminProcessesBean() {
		LOG.info(this.getClass().getSimpleName() + ": AdminProcessesBean()" );
		this.rs = ResourceBundle.getBundle("br.com.lawbook.util.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		this.authProfile = PROFILE_SERVICE.getAuthorizedUserProfile();
		LOG.info(this.getClass().getSimpleName() + ": ManagedBean Created" );
	}

	@PostConstruct
	public void loadProcesses() {
		LOG.info(this.getClass().getSimpleName() + ": loadProcesses()" );

		if (this.processes == null) {
			this.processes = new LazyDataModel<Process>() {

				private static final long serialVersionUID = 8627936003266350591L;

				@Override
				public List<Process> load(final int first, final int pageSize, final String sortField, final boolean sortOrder, final Map<String, String> filters) {
					final List<Process> processes = PROCESS_SERVICE.getAll(first, pageSize);
					LOG.info(this.getClass().getSimpleName() + ": All processes loaded" );
					return processes;
				}
			};
			this.processes.setRowCount(PROCESS_SERVICE.getProcessCount());
		}
	}

	public void chooseProcess(final ActionEvent event) {
		LOG.info(this.getClass().getSimpleName() + ": chooseProcess(ActionEvent event)");
		final Process process = (Process) this.processes.getRowData();
		this.processId = process.getId();
	}

	public String editProcess() {
		LOG.info(this.getClass().getSimpleName() + ": editProcess()");
		String outcome = "";
		if (this.processId == null) {
			LOG.warning("Occured a problem in process edition, process maybe isn't being set correctly");
			FacesUtil.warnMessage("=|", this.rs.getString("msg_processProblem"));
		} else {
			outcome = "editProcess?processId=" + this.processId + "&mode=edit&faces-redirect=true";
		}
		return outcome;
	}

	public LazyDataModel<Process> getProcesses() {
		return this.processes;
	}

	public Profile getAuthProfile() {
		return this.authProfile;
	}

}
