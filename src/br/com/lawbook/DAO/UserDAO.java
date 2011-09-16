package br.com.lawbook.DAO;

import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 15SEP2011-01
 *  
 */

public interface UserDAO extends GenericDAO<User>{

	boolean checkIfExist(String email, String userName);

}
