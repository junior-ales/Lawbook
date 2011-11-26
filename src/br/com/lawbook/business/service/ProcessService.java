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
 * @version 22NOV2011-01
 *
 */
public final class ProcessService implements Serializable {

	private static ProcessService instance;
	private static final long serialVersionUID = 8239241104971796396L;

	private ProcessService() {
	}

	public static ProcessService getInstance() {
		if (instance == null) {
			instance = new ProcessService();
		}
		return instance;
	}

	public void create(final Process process) throws IllegalArgumentException, HibernateException  {
		JavaUtil.validateParameter(process, "#### Parameter required: ProcessService.create.process");
		final ProcessDAO dao = new ProcessDAOImpl();
		dao.create(process);
	}

	public void update(final Process process) throws IllegalArgumentException, HibernateException {
		// TODO
//		JavaUtil.validateParameter(process, "Parameter required: ProcessService.update.process");
//		ProcessDAO dao = new ProcessDAOImpl();
//		dao.update(process);
	}

	public List<Process> getByResponsible(final Profile responsible) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(responsible, "#### Parameter required: ProcessService.getByResponsible.responsible");
		final ProcessDAO dao = new ProcessDAOImpl();
		return dao.getByResponsible(responsible);
	}

	public List<Process> getByPetitioner(final Profile petitioner) {
		JavaUtil.validateParameter(petitioner, "#### Parameter required: ProcessService.getByPetitioner.petitioner");
		final ProcessDAO dao = new ProcessDAOImpl();
		return dao.getByPetitioner(petitioner);
	}

	public List<Process> getByDefendant(final Profile defendant) {
		JavaUtil.validateParameter(defendant, "#### Parameter required: ProcessService.getByDefendant.defendant");
		final ProcessDAO dao = new ProcessDAOImpl();
		return dao.getByDefendant(defendant);
	}

	public Process getById(final Long processId) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(processId, "#### Parameter required: ProcessService.getById.processId");
		final ProcessDAO dao = new ProcessDAOImpl();
		return dao.getById(processId);
	}

	public List<Process> getMyProcesses(final Profile profile) throws IllegalArgumentException, HibernateException {
		JavaUtil.validateParameter(profile, "#### Parameter required: ProcessService.getMyProcesses.profile");
		final ProcessDAO dao = new ProcessDAOImpl();
		return dao.getMyProcesses(profile);
	}

}
