package test;

import static org.junit.Assert.*;
import model.User;

import org.junit.Test;
/**
 * Junit-Testcases for User
 * @author Melanie Goebel
 * @version 2014-05-21
 */
public class UserTest {

	/**
	 * Test Constructor with no parameter
	 */
	@Test
	public void testUser() {
		User u = new User();
	}
	
	/**
	 * Test Constructor with one String-Paramter
	 */
	@Test
	public void testUserString() {
		User u = new User("Name");
		assertEquals("Name",u.getUsername());
	}
 
	/**
	 * Test Constructor with one String-Paramter setting null
	 */
	@Test
	public void testUserStringNull() {
		User u = new User(null);
		assertEquals("User",u.getUsername());
	}
 
	/**
	 * Test Constructor with one String-Paramter setting empty
	 */
	@Test
	public void testUserStringEmpty() {
		User u = new User("");
		assertEquals("User",u.getUsername());
	}

	/**
	 * Test Constructor with two String-Paramter setting validate values
	 */
	@Test
	public void testUserStringStringRight1() {
		User u = new User("Melly","mgoebel@ain.at");
		assertEquals("mgoebel@ain.at",u.geteMail());		
	}
	/**
	 * Test Constructor with two String-Paramter setting validate values
	 */
	@Test
	public void testUserStringStringRight2() {
		User u = new User("Melly","melanie.goebel@ain.at");
		assertEquals("melanie.goebel@ain.at",u.geteMail());		
	}
	
	/**
	 * Test Constructor with two String-Paramter setting validate values
	 */
	@Test
	public void testUserStringStringRight3() {
		User u = new User("Melly","melanie.goebel@ain.ac.at");
		assertEquals("melanie.goebel@ain.ac.at",u.geteMail());		
	}
	/**
	 * Test Constructor with two String-Paramter setting validate values
	 */
	@Test
	public void testUserStringStringRight5() {
		User u = new User("User1","abcds");
		assertEquals("no@email.com",u.geteMail());		
	}
	/**
	 * Test Constructor with two String-Paramter setting username null
	 */
	@Test
	public void testUserStringStringNull1() {
		User u = new User(null,"asvas");
		assertEquals("User",u.getUsername());		
	}
	/**
	 * Test Constructor with two String-Paramter setting e-mail null
	 */
	@Test
	public void testUserStringStringNull2() {
		User u = new User("Name",null);
		assertEquals("no@email.com",u.geteMail());		
	}
	/**
	 * Test Constructor with two String-Paramter setting username empty
	 */
	@Test
	public void testUserStringStringEmpty1() {
		User u = new User("","address@email.com");
		assertEquals("User",u.getUsername());		
	}
	/**
	 * Test Constructor with two String-Paramter setting e-mail empty
	 */
	@Test
	public void testUserStringStringEmpty2() {
		User u = new User("Name","");
		assertEquals("no@email.com",u.geteMail());		
	}
    /**
     * Test Method toString() with validate values
     */
	@Test
	public void testToString() {
		User u = new User("Name","user@mail.at");
		assertEquals("username:Name - email:user@mail.at",u.toString());
	}
	 /**
     * Test Method toString() setting e-mail and username null
     */
	@Test
	public void testToStringNull() {
		User u = new User(null,null);
		assertEquals("username:User - email:no@email.com",u.toString());
	}

}
