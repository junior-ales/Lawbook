package br.com.lawbook.DAO.hibernate.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import br.com.lawbook.DAO.FactoryDAO;
import br.com.lawbook.DAO.PostDAO;
import br.com.lawbook.model.Post;

public class PostsTest {

	@Test
	public void getProfileWallTest() {
		FactoryDAO factory = FactoryDAO.getFactoryDAO();
		PostDAO dao = factory.getPostDAO();
		List<Post> wallPosts = dao.getProfileWall(10L);
		
		assertTrue(wallPosts.size() == 3);
		for (Post p : wallPosts) {
			System.out.println(p.content);
		}
	}

}
