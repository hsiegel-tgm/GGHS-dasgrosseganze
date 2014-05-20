package test;

import static org.junit.Assert.*;
import model.User;

import org.junit.Test;

public class UserTest {

	@Test
	public void testUser() {

	}

	@Test
	public void testUserString() {
	
	}

	@Test
	public void testUserStringStringRight1() {
		User u = new User("Melly","mgoebel@ain.at");
		assertEquals("mgoebel@ain.at",u.geteMail());		
	}

	@Test
	public void testGetUsername() {
	
	}

	@Test
	public void testToString() {
	
	}

}
