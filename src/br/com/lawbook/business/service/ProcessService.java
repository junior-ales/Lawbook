package br.com.lawbook.business.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.ProcessDAO;
import br.com.lawbook.dao.impl.ProcessDAOImpl;
import br.com.lawbook.model.Process;
import br.com.lawbook.model.Profile;
import static br.com.lawbook.util.JavaUtil.*;

/**
 * @author Edilson Luiz Ales Junior
 * @version 03DEC2011-04
 *
 */
public final class ProcessService implements Serializable {

	private final ProcessDAO dao;
	private static ProcessService instance;
	private static final long serialVersionUID = 5791197051721076105L;

	private ProcessService() {
		this.dao = new ProcessDAOImpl();
	}

	public static ProcessService getInstance() {
		if (instance == null) {
			instance = new ProcessService();
		}
		return instance;
	}

	public void create(final Process process) throws IllegalArgumentException, HibernateException  {
		validateParameter(process, "#### Parameter required: ProcessService.create.process");
		this.dao.create(process);
	}

	public void update(final Process process) throws IllegalArgumentException, HibernateException {
		validateParameter(process, "#### Parameter required: ProcessService.update.process");
		this.dao.update(process);
	}

	public List<Process> getAll() throws HibernateException {
		return this.dao.getAll();
	}

	public Process getById(final Long processId) throws IllegalArgumentException, HibernateException {
		validateParameter(processId, "#### Parameter required: ProcessService.getById.processId");
		return this.dao.getById(processId);
	}

	public List<Process> getMyProcesses(final Profile profile) throws IllegalArgumentException, HibernateException {
		validateParameter(profile, "#### Parameter required: ProcessService.getMyProcesses.profile");
		return this.dao.getMyProcesses(profile);
	}

	public int getProcessCount() throws HibernateException {
		return Integer.parseInt(this.dao.getProcessesCount().toString());
	}

}
