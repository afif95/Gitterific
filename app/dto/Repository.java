package dto;

import java.util.ArrayList;
import java.util.List;

public class Repository {
	
	public String full_name;
	private String name;
	private OwnerInfo Owner;
	private List<String> topics;

	/**
	 * Default Constructor
	 */
	public Repository() {}
	
	/**
	 * Constructor with repository's full name and name.
	 * 
	 * @param full_name
	 * @param name
	 */
	public Repository(String full_name,String name) {
		this.name=name;
		this.full_name=full_name;
	}
	
	
	/**
	 * This method returns the name of the repository.
	 * @return repository's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method sets the name of the repository.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method returns the full name of the repository.
	 * @return repository's full name
	 */
	public String getFull_name() {
		return full_name;
	}
	
	/**
	 * This method sets the full name of the repository.
	 * @param full_name
	 */
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	
	/**
	 * This method returns the owner of the repository.
	 * @return repository's owner
	 */
	public OwnerInfo getOwner() {
		return Owner;
	}
	
	/**
	 * This method sets the owner of the repository.
	 * @param Owner
	 */
	public void setOwner(OwnerInfo owner) {
		Owner = owner;
	}
	
	/**
	 * This method returns the list of topics of the repository.
	 * @return repository's topic's list
	 */
	public List<String> getTopics() {
		return topics;
	}
	
	/**
	 * This method sets the topics of the repository.
	 * 
	 * @param topics
	 */
	public void setTopics(List<String> topics) {
		this.topics = topics;
	}
	
	static List<Repository> repoList = new ArrayList<>();
	
	/**
	 * This method adds a repository to the list.
	 * 
	 * @param repo this is a repository
	 */
	public static void addRepo(Repository repo) {
		repoList.add(repo);
	}
	
	/**
	 * This method adds multiple repositories to the list.
	 * 
	 * @param repo list of repositories
	 */
	public static void addAllRepo(List<Repository> repo) {
		repoList.addAll(repo);
	}
	
	/**
	 * This method returns a list of repositories.
	 */
	public static List<Repository> getRepos() {
		return repoList;
	}

	
	
}
