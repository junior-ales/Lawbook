package br.com.lawbook.business;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.lawbook.dao.ProcessDAO;
import br.com.lawbook.model.Process;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.HibernateUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 22NOV2011-01
 *
 */
public class ProcessDAOImpl implements ProcessDAO {

	private final static Logger LOG = Logger.getLogger("ProcessDAOImpl");

	@Override
	public void create(final Process process) throws HibernateException {
		final Session session = HibernateUtil.getSession();
		final Transaction tx = session.beginTransaction();
		try {
			session.save(process);
			tx.commit();
		} catch (final Exception e) {
			tx.rollback();
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("#### Hibernate Session closed");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Process> getByResponsible(final Profile responsible) throws HibernateException {
		final Session session = HibernateUtil.getSession();
		try {
			final Criteria crit = session.createCriteria(Process.class);
			crit.add(Restrictions.eq("responsible", responsible));
			return crit.list();
		} catch (final Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("#### Hibernate Session closed");
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Process> getByPetitioner(final Profile petitioner) throws HibernateException {
		final Session session = HibernateUtil.getSession();
		try {
			final Criteria crit = session.createCriteria(Process.class);
			crit.add(Restrictions.eq("petitioner", petitioner));
			return crit.list();
		} catch (final Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("#### Hibernate Session closed");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Process> getByDefendant(final Profile defendant) throws HibernateException {
		final Session session = HibernateUtil.getSession();
		try {
			final Criteria crit = session.createCriteria(Process.class);
			crit.add(Restrictions.eq("defendant", defendant));
			return crit.list();
		} catch (final Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("#### Hibernate Session closed");
		}
	}
	
	@Override
	public List<Process> getMyProcesses(Profile profile) throws HibernateException {
		List<Process> processes = new ArrayList<Process>();
		processes.addAll(getByResponsible(profile));
		processes.addAll(getByDefendant(profile));
		processes.addAll(getByPetitioner(profile));
		return processes;
	}
	
	@Override
	public Process getById(final Long processId) throws HibernateException {
		final Session session = HibernateUtil.getSession();
		try {
			final Criteria crit = session.createCriteria(Process.class);
			crit.add(Restrictions.eq("id", processId));
			return (Process) crit.uniqueResult();
		} catch (final Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("#### Hibernate Session closed");
		}
	}

}
