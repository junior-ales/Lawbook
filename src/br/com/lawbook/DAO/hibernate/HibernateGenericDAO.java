package br.com.lawbook.DAO.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.lawbook.DAO.GenericDAO;

/**
 * @author Edilson Luiz Ales Junior
 * @version 21OCT2011-02
 * 
 */

@SuppressWarnings("unchecked")
public class HibernateGenericDAO<T> implements GenericDAO<T> {

    private Class<T> persistentClass;
    private Session session;
    private static Logger LOG = LoggerFactory.getLogger(HibernateGenericDAO.class);

	public HibernateGenericDAO(Session session) {
        this.session = session;
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Session getSession() {
        return this.session;
    }

    @Override
    public T getById(Serializable id) {
        return (T) this.session.get(this.persistentClass, id);
    }

    @Override
    public List<T> getAll() {
        return this.session.createCriteria(this.persistentClass).list();
    }

    @Override
    public T save(T entity) {
		try {
			this.session.save(entity);
			return entity;
		} catch (HibernateException e) {
			LOG.error(e.getMessage());
			throw new HibernateException("Problem saving new register: " + e.getMessage());
		}
    }
    
    public T update(T entity) {
    	try {
			this.session.update(entity);
			return entity;
		} catch (HibernateException e) {
			LOG.error(e.getMessage());
			throw new HibernateException("Problem updating new register: " + e.getMessage());
		}
    }

    @Override
    public void delete(T entity) {
        this.session.delete(entity);
    }

}