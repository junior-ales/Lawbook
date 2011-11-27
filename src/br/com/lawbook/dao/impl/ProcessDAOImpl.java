package br.com.lawbook.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.lawbook.dao.ProcessDAO;
import br.com.lawbook.model.Process;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.HibernateUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 23NOV2011-02
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
	@SuppressWarnings("unchecked")
	public List<Process> getMyProcesses(Profile profile) throws HibernateException {
		Session session = HibernateUtil.getSession();
		try {
			StringBuilder stringQuery = new StringBuilder();
			stringQuery.append("from lwb_process where defendant_id = :profileId ")
					   .append("or petitioner_id = :profileId or responsible_id = :profileId ")
					   .append("order by opening_date desc");
			Query query = session.createQuery(stringQuery.toString());
			query.setParameter("profileId", profile.getId());
			return (List<Process>) query.list();
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			throw new HibernateException(e);
		} finally {
			session.close();
			LOG.info("Hibernate Session closed");
		}
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
