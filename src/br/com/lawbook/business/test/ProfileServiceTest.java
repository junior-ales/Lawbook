package br.com.lawbook.business.test;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;

import br.com.lawbook.model.Comment;
import br.com.lawbook.model.Post;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.DBMock;

/**
 * @author Edilson Luiz Ales Junior
 * @version 12SEP2011-02 
 */

public class ProfileServiceTest {
	
	@Test
	public void getStreamTest() {
		DBMock db = DBMock.getInstance();
		List<Profile> profiles = db.getTableProfile();
		List<Post> posts = db.getTablePost();
		Profile pf = profiles.get(3);
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println(pf.getFirstName() + "'s Wall\n");
		for (Post p : posts) {
			if (p.getReceivers() == null || p.getReceivers().contains(pf)){
				System.out.println(p.getSender().getFirstName() + " wrote: " + p.getContent() + " at " + df.format(p.getDateTime().getTime()));
				System.out.println("Comments");
				for (Comment c : p.getComments()) {
					System.out.println(c.getSender().getFirstName() + " comment: " + c.getContent() + " at " + df.format(c.getDateTime().getTime()));
				}
			}
		}
	}
	
//	@Test
//	public void getWallTest() {
//		DBMock db = DBMock.getInstance();
//		List<Profile> profiles = db.getTableProfile();
//		List<Post> posts = db.getTablePost();
//		Profile pf = profiles.get(3);
//		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//		System.out.println(pf.getFirstName() + "'s Wall\n");
//		for (Post p : posts) {
//			if (p.getReceivers() == null || p.getReceivers().contains(pf)){
//				System.out.println(p.getSender().getFirstName() + " wrote: " + p.getContent() + " at " + df.format(p.getDateTime().getTime()));
//				System.out.println("Comments");
//				for (Comment c : p.getComments()) {
//					System.out.println(c.getSender().getFirstName() + " comment: " + c.getContent() + " at " + df.format(c.getDateTime().getTime()));
//				}
//			}
//		}
//	}
	
}
