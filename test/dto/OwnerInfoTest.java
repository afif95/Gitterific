package dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * This tests the <code>OwnerInfo</code> class.
 * @author Roxane
 *
 */

public class OwnerInfoTest {
	OwnerInfo defaultOwner = new OwnerInfo();
	OwnerInfo owner = new OwnerInfo("name");
	/**
	 * This tests the default constructor.
	 */
	@Test
	public void testDefaultConstructor() {
		assertNull(defaultOwner.getLogin());
	}
	
	/**
	 * This tests the  constructor that takes
	 * an owner's login as a paramter.
	 */
	@Test
	public void testConstructor() {
		assertEquals("name", owner.getLogin());
	}
	
	/**
	 * This tests if the login is correctly set or not.
	 */
	@Test
	public void testSetLogin() {
		defaultOwner.setLogin("OtherName");
		assertEquals("OtherName", defaultOwner.getLogin());
	}
}