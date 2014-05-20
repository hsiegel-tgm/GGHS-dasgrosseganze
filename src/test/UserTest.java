package test;

import static org.junit.Assert.*;
import model.User;

import org.junit.Test;

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
	public void testToString() {
	   User u = new User("Name","user@mail.at");
	   assertEquals("username:Name - email:user@mail.at",u.toString());
	}

}
