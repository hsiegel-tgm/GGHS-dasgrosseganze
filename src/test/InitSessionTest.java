/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.junit.Test;

import com.example.myproject2.InitSession;

/**
 * @author Melanie Goebel
 *
 */
public class InitSessionTest {

	/**
	 * Test method for {@link com.example.myproject2.InitSession#buildsf()}.
	 */
	@Test
	public void testBuildsf() {
		SessionFactory s = InitSession.buildsf();
	}

	/**
	 * Test method for {@link com.example.myproject2.InitSession#getSession()}.
	 */
	@Test
	public void testGetSession() {
		fail("Not yet implemented");
	}

}
