package br.com.lawbook.dao;

import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Process;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27NOV2011-02
 *
 */
public interface ProcessDAO {

	void create(Process process) throws HibernateException;
	
	void update(Process process) throws HibernateException;

	Process getById(Long processId) throws HibernateException;

	List<Process> getMyProcesses(Profile profile) throws HibernateException;

}
