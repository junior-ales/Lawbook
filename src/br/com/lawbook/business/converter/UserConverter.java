package br.com.lawbook.business.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.HibernateException;

import br.com.lawbook.business.service.UserService;
import br.com.lawbook.model.User;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 19NOV2011-01
 *  
 */
public class UserConverter implements Converter {

	public static List<User> users;
	
	static {
		try {
			users = UserService.getInstance().getAll();
		} catch (HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		if (submittedValue.trim().equals("")) {
            return null;
        } else {
            for (User u : users) {
                if (u.getUserName().equalsIgnoreCase(submittedValue)) return u;
            }
        }
        return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((User) value).getUserName());
        }

	}

}
