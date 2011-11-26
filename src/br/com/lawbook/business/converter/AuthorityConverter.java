package br.com.lawbook.business.converter;

import java.util.List;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.HibernateException;

import br.com.lawbook.business.service.AuthorityService;
import br.com.lawbook.model.Authority;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 25NOV2011-02
 *  
 */
public class AuthorityConverter implements Converter {

	public static List<Authority> authorities;
	private final static Logger LOG = Logger.getLogger("AuthorityConverter");
	
	static {
		try {
			authorities = AuthorityService.getInstance().getAll();
			LOG.info("#### Authorities fetched");
		} catch (HibernateException e) {
			LOG.severe("#### Authorities not fetched");
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String authName) {
		if (authName.trim().equals("")) {
			LOG.warning("#### Authority name is null or empty");
            return null;
        } else {
            for (Authority auth : authorities) {
                if (auth.getName().equalsIgnoreCase(authName)) {
                	LOG.info("#### Authority " + authName + " fetched");
                	return auth;
                }
            }
        }
		LOG.warning("#### Authority not found");
        return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object authority) {
		if (authority == null || authority.equals("")) {
			LOG.warning("#### Authority is null");
            return "";
        } else {
        	LOG.info("#### Authority fetched");
            return String.valueOf(((Authority) authority).getName());
        }

	}

}
