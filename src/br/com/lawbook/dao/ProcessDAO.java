package br.com.lawbook.dao;

import java.util.List;

import org.hibernate.HibernateException;

import br.com.lawbook.model.Process;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 22NOV2011-01
 *
 */
public interface ProcessDAO {

	void create(Process process) throws HibernateException;

	Process getById(Long processId) throws HibernateException;

	List<Process> getByResponsible(Profile responsible) throws HibernateException;

	List<Process> getMyProcesses(Profile profile) throws HibernateException;

	List<Process> getByPetitioner(Profile petitioner) throws HibernateException;

	List<Process> getByDefendant(Profile defendant) throws HibernateException;

}
