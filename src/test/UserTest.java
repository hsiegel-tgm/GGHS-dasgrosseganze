package test;

import static org.junit.Assert.*;
import model.User;

import org.junit.Test;
/**
 * Junit-Testcases for User
 * @author Melanie Göbel
 * @version 2014-05-21
 */
public class UserTest {

	@Test
	public void testUser() {
		User u = new User();
	}

	@Test
	public void testUserString() {
		User u = new User("Name");
		assertEquals("Name",u.getUsername());
	}

	@Test
	public void testUserStringNull() {
		User u = new User(null);
		assertEquals("User",u.getUsername());
	}

	@Test
	public void testUserStringEmpty() {
		User u = new User("");
		assertEquals("User",u.getUsername());
	}

	@Test
	public void testUserStringStringRight1() {
		User u = new User("Melly","mgoebel@ain.at");
		assertEquals("mgoebel@ain.at",u.geteMail());		
	}
	@Test
	public void testUserStringStringRight2() {
		User u = new User("Melly","melanie.goebel@ain.at");
		assertEquals("melanie.goebel@ain.at",u.geteMail());		
	}

	@Test
	public void testUserStringStringRight3() {
		User u = new User("Melly","melanie.goebel@ain.ac.at");
		assertEquals("melanie.goebel@ain.ac.at",u.geteMail());		
	}

	@Test
	public void testUserStringStringRight5() {
		User u = new User("User1","abcds");
		assertEquals("no@email.com",u.geteMail());		
	}

	@Test
	public void testUserStringStringNull1() {
		User u = new User(null,"asvas");
		assertEquals("User",u.getUsername());		
	}

	@Test
	public void testUserStringStringNull2() {
		User u = new User("Name",null);
		assertEquals("no@email.com",u.geteMail());		
	}

	@Test
	public void testUserStringStringEmpty1() {
		User u = new User("","address@email.com");
		assertEquals("User",u.getUsername());		
	}

	@Test
	public void testUserStringStringEmpty2() {
		User u = new User("Name","");
		assertEquals("no@email.com",u.geteMail());		
	}

	@Test
	public void testUserStringStringEmpty() {
		User u = new User(null,"asvas");
		assertEquals("User",u.getUsername());		
	}

	@Test
	public void testToString() {
		User u = new User("Name","user@mail.at");
		assertEquals("username:Name - email:user@mail.at",u.toString());
	}

	@Test
	public void testToStringNull() {
		User u = new User(null,null);
		assertEquals("username:User - email:no@email.com",u.toString());
	}

}
