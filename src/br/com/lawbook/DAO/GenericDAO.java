package br.com.lawbook.DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

/**
 * @author Edilson Luiz Ales Junior
 * @version 21OCT2011-02 
 * 
 */

public interface GenericDAO<T> {

    public Session getSession();
    public T getById(Serializable id);
    public List<T> getAll();
    public T save(T entity);
    public T update(T entity);
    public void delete(T entity);
}