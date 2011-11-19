package br.com.lawbook.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

import br.com.lawbook.model.User;
import br.com.lawbook.util.FacesUtil;
import br.com.lawbook.util.UserConverter;

/**
 * @author Edilson Luiz Ales Junior
 * @version 19NOV2011-01
 *  
 */
@ManagedBean
@RequestScoped
public class AdminBean {
	
	private List<User> users;
	private User chosenUser;
	private String usernameComplete;
//	private final static Logger LOG = Logger.getLogger("AdminBean");
	
	public AdminBean() {
		this.chosenUser = new User();
		this.users = UserConverter.users;
	}

	public void onTabChange(TabChangeEvent event) {
		this.chosenUser = new User();
	}
	
	public List<User> completeUsers(String query) {
		if (query == null) query = "";
        List<User> results = new ArrayList<User>();
        for (User u : this.users) {
            if (u.getUserName().startsWith(query)) {
                results.add(u);
            }
        }
        return results;
    }

	public void updateUser() { 
		// TODO update chosenUser in database 
		FacesUtil.infoMessage("=)", "Updating user");
	}
	
	public void handleSelect(SelectEvent event) {  
		FacesUtil.infoMessage("=)", "Handling select");
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getChosenUser() {
		return chosenUser;
	}

	public void setChosenUser(User chosenUser) {
		this.chosenUser = chosenUser;
	}

	public String getUsernameComplete() {
		return usernameComplete;
	}

	public void setUsernameComplete(String usernameComplete) {
		this.usernameComplete = usernameComplete;
	}

}
