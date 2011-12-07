package br.com.lawbook.dao;

import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Process;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 06DEC2011-06
 *
 */
public interface ProcessDAO {

	void create(Process process) throws HibernateException;
	
	void update(Process process) throws HibernateException;
	
	List<Process> getAll(int first, int pageSize) throws HibernateException;

	Process getById(Long processId) throws HibernateException;

	List<Process> getMyProcesses(Profile profile, int first, int pageSize) throws HibernateException;

	Long getProcessesCount() throws HibernateException;

}
