package br.com.lawbook.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * @author Edilson Luiz Ales Junior
 * @version 07NOV2011-02
 * 
 */
public class FacesUtil {

	public static void infoMessage(String summary, String message) {
		showMessage(FacesMessage.SEVERITY_INFO, summary, message);
	}

	public static void warnMessage(String summary, String message) {
		showMessage(FacesMessage.SEVERITY_WARN, summary, message);
	}

	public static void errorMessage(String summary, String message) {
		showMessage(FacesMessage.SEVERITY_ERROR, summary, message);
	}

	private static void showMessage(FacesMessage.Severity severity,	String summary, String message) {
		FacesMessage facesMessage = new FacesMessage(severity, summary, message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

}
