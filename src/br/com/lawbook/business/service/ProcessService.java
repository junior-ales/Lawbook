package br.com.lawbook.business.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.dao.ProcessDAO;
import br.com.lawbook.dao.impl.ProcessDAOImpl;
import br.com.lawbook.model.Process;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.JavaUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27NOV2011-02
 *
 */
public final class ProcessService implements Serializable {

	private final ProcessDAO dao;
	private static ProcessService instance;
	private static final long serialVersionUID = -7745879838206932661L;

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
		JavaUtil.validateParameter(process, "#### Parameter required: ProcessService.create.process");
		this.dao.create(process);
	}

	public void update(final Process process) throws IllegalArgumentException, HibernateException {
		// TODO
//		JavaUtil.validateParameter(process, "Parameter required: ProcessService.update.process");
//		ProcessDAO dao = new ProcessDAOImpl();
//		dao.update(process);
	}

	public Process getById(final Long processId) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(processId, "#### Parameter required: ProcessService.getById.processId");
		return this.dao.getById(processId);
	}

	public List<Process> getMyProcesses(final Profile profile) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(profile, "#### Parameter required: ProcessService.getMyProcesses.profile");
		return this.dao.getMyProcesses(profile);
	}

}
