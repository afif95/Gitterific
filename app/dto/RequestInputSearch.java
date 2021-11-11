package dto;

import java.util.List;

public class RequestInputSearch {
	
	private String full_name;
	private String name;
	private OwnerInfo Owner;
	private List<String> topics;


	public RequestInputSearch() {}
	
	public RequestInputSearch(String full_name,String name) {
		this.name=name;
		this.full_name=full_name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	
	public OwnerInfo getOwner() {
		return Owner;
	}

	public void setOwner(OwnerInfo owner) {
		Owner = owner;
	}
	
	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	
	
}