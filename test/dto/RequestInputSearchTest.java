package dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


import java.util.Arrays;
import java.util.List;

public class RequestInputSearchTest {
	RequestInputSearch test = new RequestInputSearch("TestFullName", "testName");
	RequestInputSearch testNull = new RequestInputSearch();
	OwnerInfo owner = new OwnerInfo("someName");
	List<String> topics = Arrays.asList("topic1", "topic2");
	
	@Test
	public void testGet () {
		assertEquals("TestFullName", test.getFull_name());
		assertEquals("testName", test.getName());
	}
	
	@Test
	public void testSetName() {
		test.setName("OtherName");
		assertEquals("OtherName", test.getName());
	}
	
	@Test
	public void testSetFullName() {
		test.setFull_name("OtherFullName");
		assertEquals("OtherFullName", test.getFull_name());
	}
	
	@Test
	public void testSetOwner() {
		test.setOwner(owner);
		assertEquals(owner, test.getOwner());
	}
	
	@Test 
	public void testTopics() {
		test.setTopics(topics);
		assertEquals(topics, test.getTopics());
	}
	
	@Test
	public void testDefaultConstructor() {
		assertEquals(null, testNull.getName());
		assertEquals(null, testNull.getFull_name());
		assertEquals(null, testNull.getOwner());
		assertEquals(null, testNull.getTopics());
	}
}