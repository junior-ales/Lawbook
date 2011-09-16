package br.com.lawbook.managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.lawbook.business.LocationService;

/**
 * @author Edilson Luiz Ales Junior
 * @version 16SEP2011-01
 */
@ManagedBean
@RequestScoped
public class LocationBean {
	
	LocationService service = LocationService.getInstance(); 
}
