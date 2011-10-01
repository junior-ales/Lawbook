package br.com.lawbook.DAO;

import br.com.lawbook.model.User;

/**
 * @author Edilson Luiz Ales Junior
 * @version 01OCT2011-02
 *  
 */

public interface UserDAO extends GenericDAO<User>{

	boolean checkIfExist(String email, String userName);

	User getByUserName(String userName);

}
