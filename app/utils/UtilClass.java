package utils;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.routes;
import dto.*;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;


public class UtilClass {
	
	public List<Repository> JSONtoRepoList(JsonNode node) {
		return StreamSupport.stream( node.get("items").spliterator(), false)
                .map(sObj -> Json.fromJson(sObj, Repository.class))
                .collect(Collectors.toList());
	}
	
	public PublicOwnerInfo getPublicOwnerInfo(JsonNode node) {	 
    	return Json.fromJson(node.get("owner"), PublicOwnerInfo.class);
    }
	
	
	public PublicRepositoryInfo getPublicRepositoryInfo(JsonNode node) {
    	return  Json.fromJson(node, PublicRepositoryInfo.class);    
	}
	
	public List<String> getIssuesRepo(JsonNode node) {
		return node.findValues("title").stream().map(JsonNode::asText)
		.collect(Collectors.toList());
	}
	
	public List<String> getOwnerRepos(JsonNode node) {
    	return node.findValues("name").stream().map(JsonNode::asText)
    			.collect(Collectors.toList());
	}
	
	public HashMap <String, PublicOwnerInfo> insertKeyInOwnerMap(JsonNode node, HashMap <String, PublicOwnerInfo> ownerMap, String searchKey) {
		
    	PublicOwnerInfo p = Json.fromJson(node, PublicOwnerInfo.class);
    	if(!ownerMap.containsKey(searchKey)) {
    		ownerMap.put(searchKey, p);
    	}
    	
    	return ownerMap;
    
	}
	
	public HashMap <String, List<Repository>> addRepoToUserMap(String s, HashMap <String, List<Repository>> user_searches, List<Repository> list) {

    	List<Repository> tmp = user_searches.get(s);
    	if(tmp ==  null) {
    		user_searches.put(s, new ArrayList<Repository>());
    	}
    	
    	if(user_searches.get(s).size() >= 100) {
    		List<Repository> l = user_searches.get(s);
    		for (int i = 0; i < user_searches.get(s).size(); i++) {
    			l.subList(90, l.size()).clear();
    		}
    		user_searches.replace(s, l);
    	}
    	
    	
    	user_searches.get(s).addAll(0,  list);
    	return user_searches;
    
	}
	
	public Map<String, Integer> getIssues(List<String> s){
		Map<String, Integer> freq = s.parallelStream().flatMap(sob -> Arrays.asList(sob.split(" ")).stream()).collect(Collectors.toConcurrentMap(sob1->sob1, sob1 ->1, Integer::sum));
		return freq;
	}
	
	

}
