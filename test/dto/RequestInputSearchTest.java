package dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
	
	/**
	 * This test checks the <code> addRepo </code>method.
	 * It checks that the repository list contains the new repository.
	 */
	@Test
	public void testAddRepo() {
		Repository repo = new Repository("otherFullName", "otherName");
		Repository.addRepo(repo);
		assertTrue(Repository.repoList.contains(repo));
	}
	
	/**
	 * This test checks the <code> addAllRepo </code>method.
	 * It checks that the repository list contains all the repositories 
	 * that were added.
	 */
	@Test
	public void testAddAllRepo() {
		Repository repo1 = new Repository("otherFullName1", "otherName1");
		Repository repo2 = new Repository("otherFullName2", "otherName2");
		Repository repo3 = new Repository("otherFullName3", "otherName3");
		List<Repository> listRepo = new ArrayList<>();
		listRepo.add(repo1);
		listRepo.add(repo2);
		listRepo.add(repo3);
		
		Repository.addAllRepo(listRepo);
		assertTrue(Repository.repoList.contains(repo1));
		assertTrue(Repository.repoList.contains(repo2));
		assertTrue(Repository.repoList.contains(repo3));
	}
	
	/**
	 * This test checks the <code> getRepo </code>method.
	 * It checks that the repository list is equal to the original list 
	 * of repositories.
	 */
	@Test
	public void testGetRepos() {
		Repository repo = new Repository("otherFullName", "otherName");
		List<Repository> listRepo = new ArrayList<>();
		listRepo.add(repo);
		Repository.addAllRepo(listRepo);
		assertEquals(listRepo, Repository.getRepos());
	}
}