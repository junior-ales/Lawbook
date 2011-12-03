package br.com.lawbook.dao;

import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Process;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 03DEC2011-04
 *
 */
public interface ProcessDAO {

	void create(Process process) throws HibernateException;
	
	void update(Process process) throws HibernateException;
	
	List<Process> getAll() throws HibernateException;

	Process getById(Long processId) throws HibernateException;

	List<Process> getMyProcesses(Profile profile) throws HibernateException;

	Long getProcessesCount() throws HibernateException;

}
