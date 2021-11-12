package dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class OwnerInfoTest {
	OwnerInfo defaultOwner = new OwnerInfo();
	OwnerInfo owner = new OwnerInfo("name");
	@Test
	public void testDefaultConstructor() {
		assertNull(defaultOwner.getLogin());
	}
	
	@Test
	public void testConstructor() {
		assertEquals("name", owner.getLogin());
	}
	
	@Test
	public void testSetLogin() {
		defaultOwner.setLogin("OtherName");
		assertEquals("OtherName", defaultOwner.getLogin());
	}
}