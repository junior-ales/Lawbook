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
 * @version 05DEC2011-05
 *
 */
public final class ProcessService implements Serializable {

	private final ProcessDAO dao;
	private static ProcessService instance;
	private static final long serialVersionUID = -4463585239437254187L;

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

	public List<Process> getMyProcesses(final Profile profile, final int first, final int pageSize) throws IllegalArgumentException, HibernateException {
		validateParameter(profile, "#### Parameter required: ProcessService.getMyProcesses.profile");
		validateParameter(first, "#### Parameter required: ProcessService.getMyProcesses.first");
		validateParameter(pageSize, "#### Parameter required: ProcessService.getMyProcesses.pageSize");
		return this.dao.getMyProcesses(profile, first, pageSize);
	}

	public int getProcessCount() throws HibernateException {
		return Integer.parseInt(this.dao.getProcessesCount().toString());
	}

}
