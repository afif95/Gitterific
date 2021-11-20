package dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


import java.util.Arrays;
import java.util.List;
/**
 * This is used to test <code>OwnerInfo</code> class.
 * @author Roxane
 *
 */
public class RequestInputSearchTest {
	Repository test = new Repository("TestFullName", "testName");
	Repository testNull = new Repository();
	OwnerInfo owner = new OwnerInfo("someName");
	List<String> topics = Arrays.asList("topic1", "topic2");
	
	/**
	 * This tests the <code>getName</code> method of <code>OwnerInfo</code> class.
	 * It checks that if the name is same as what was expected.
	 */
	@Test
	public void testGet () {
		assertEquals("TestFullName", test.getFull_name());
		assertEquals("testName", test.getName());
	}
	
	/**
	 * This tests the <code>setName</code> method of <code>OwnerInfo</code> class.
	 * It checks that if the name is set correctly or not.
	 */
	@Test
	public void testSetName() {
		test.setName("OtherName");
		assertEquals("OtherName", test.getName());
	}
	
	/**
	 * This tests the <code>SetFull_Name</code> method of <code>OwnerInfo</code> class.
	 * It checks that if the full_name is set correctly or not.
	 */
	@Test
	public void testSetFullName() {
		test.setFull_name("OtherFullName");
		assertEquals("OtherFullName", test.getFull_name());
	}
	
	/**
	 * This tests the <code>SetOwner</code> method of <code>OwnerInfo</code> class.
	 * It checks that if the owner is set correctly or not.
	 */
	@Test
	public void testSetOwner() {
		test.setOwner(owner);
		assertEquals(owner, test.getOwner());
	}
	
	/**
	 * This tests the <code>SetTopics</code> method of <code>OwnerInfo</code> class.
	 * It checks that if topics are set correctly or not.
	 */
	@Test 
	public void testTopics() {
		test.setTopics(topics);
		assertEquals(topics, test.getTopics());
	}
	
	/**
	 * This tests the default constructor of <code>OwnerInfo</code> class.
	 * It checks that the object is instantiated correctly or not.
	 */
	@Test
	public void testDefaultConstructor() {
		assertEquals(null, testNull.getName());
		assertEquals(null, testNull.getFull_name());
		assertEquals(null, testNull.getOwner());
		assertEquals(null, testNull.getTopics());
	}
}