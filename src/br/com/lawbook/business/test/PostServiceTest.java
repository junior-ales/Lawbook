package br.com.lawbook.business.test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.lawbook.business.PostService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Profile;

/**
 * @author Edilson Luiz Ales Junior
 * @version 15OUT2011-02
 * 
 */

public class PostServiceTest {
	
	@Test
	public void getStreamTest() {
		ProfileService pfs = ProfileService.getInstance();
		PostService ps = PostService.getInstance(); 
		Profile profile = pfs.getProfileBy("jrales");
		assertNotNull(ps.getStream(profile));
	}
	
}
