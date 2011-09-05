package br.com.lawbook.DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

/**
 * @author Edilson Luiz Ales Junior
 * @version 04SEP2011-01 
 * 
 */

public interface GenericDAO<T> {

    public Session getSession();
    public T getById(Serializable id);
    public List<T> getAll();
    public T save(T entity);
    public void delete(T entity);
}