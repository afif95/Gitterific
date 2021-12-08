package dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

public class RepositoryTest {
	Repository repo = new Repository();
	Repository repo2 = new Repository("full name", "name");
	Repository repo3 = new Repository("full name2", "name");
	OwnerInfo owner = new OwnerInfo("name owner");
	List<String> topics = Arrays.asList("topic1", "topic2", "topic3");
	List<Repository> repos = Arrays.asList(repo, repo2);
	List<Repository> uniqueRepo = Arrays.asList(repo3);
	
	/**
	 * This method tests the getName method in Repository.java
	 */
	@Test
	public void getNameTest() {
		assertEquals("name", repo2.getName());
	}
	
	/**
	 * This method tests the setName method in Repository.java
	 */
	
	@Test
	public void setName() {
		repo.setName("name 2");
		assertEquals("name 2", repo.getName());
	}
	
	/**
	 * This method tests the getFullName method in Repository.java
	 */
	
	@Test
	public void getFullNameTest() {
		assertEquals("full name", repo2.getFull_name());
	}
	
	/**
	 * This method tests the SetFullName method in Repository.java
	 */
	
	@Test
	public void setFullNameTest() {
		repo.setFull_name("full name 2");
		assertEquals("full name 2", repo.getFull_name());
	}
	
	/**
	 * This method tests the setOwner and getOwner methods in Repository.java
	 */
	
	@Test
	public void setOwnerTest() {
		repo.setOwner(owner);
		assertEquals(owner, repo.getOwner());
	}
	
	/**
	 * This method tests the getTopics method in Repository.java
	 */
	
	@Test
	public void getTopicsTest() {
		assertEquals(null, repo.getTopics());
	}
	
	/**
	 * This method tests the setTopics method in Repository.java
	 */
	
	@Test
	public void setTopicsTest() {
		repo.setTopics(topics);
		assertEquals(topics, repo.getTopics());
	}
	
	/**
	 * This method tests the addRepo method in Repository.java
	 */
	
	@Test
	public void addRepoTest() {
		Repository.addRepo(repo3);
		assertEquals(uniqueRepo, Repository.getRepos());
	}
	
	/**
	 * This method tests the addAllRepo method in Repository.java
	 */
	
	@Test
	public void addAllRepoTest() {
		Repository.addAllRepo(repos);
		assertEquals(repo3.getFull_name(), Repository.getRepos().get(0).getFull_name());
	}
}
