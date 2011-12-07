package br.com.lawbook.business.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.HibernateException;

import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 26NOV2011-01
 * 
 */
public class ProfileConverter implements Converter {

public static List<Profile> profiles;
	
	static {
		try {
			profiles = ProfileService.getInstance().getAll();
		} catch (HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		if (submittedValue.trim().equals("")) {
            return null;
        } else {
            for (Profile p : profiles) {
                if (p.getFirstName().equalsIgnoreCase(submittedValue)) return p;
            }
        }
        return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Profile) value).getFirstName());
        }

	}

	
}
