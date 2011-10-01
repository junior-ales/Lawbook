package br.com.lawbook.managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.lawbook.business.PostService;

/**
 * @author Edilson Luiz Ales Junior
 * @version 01OCT2011-03
 */
@ManagedBean
@SessionScoped
public class PostBean {

	@SuppressWarnings("unused")
	private PostService service;

	public PostBean() {
		this.service = PostService.getInstance();
	}
}
