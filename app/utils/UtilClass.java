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
	
	/**This method is used for deserialization. 
	 * It converts the <code>JsonNode</code> data into a list of 
	 * objects of <code> Repository</code>.
	 * 
	 * 
	 * @author Amrit Singh
	 * @param node JsonNode extracted from an HTTP request 
	 * @return list of repositories
	 */
	public List<Repository> JSONtoRepoList(JsonNode node) {
		return StreamSupport.stream( node.get("items").spliterator(), false)
                .map(sObj -> Json.fromJson(sObj, Repository.class))
                .collect(Collectors.toList());
	}
	
	
	/**This method is used for deserialization. 
	 * It converts the <code>JsonNode</code> data into an objects 
	 * of <code> PublicOwnerInfo</code>.
	 * 
	 * 
	 * @author Rimsha Afzal
	 * @param node JsonNode extracted from an HTTP request 
	 * @return an object of PublicOwnerInfo
	 */
	
	public PublicOwnerInfo getPublicOwnerInfo(JsonNode node) {	 
    	return Json.fromJson(node.get("owner"), PublicOwnerInfo.class);
    }
	
	
	/**This method is used for deserialization. 
	 * It converts the <code>JsonNode</code> data into an object
	 * of <code> PublicRepositoryInfo</code>.
	 * 
	 * 
	 * @author Roxane Tissier
	 * @param node JsonNode extracted from an HTTP request 
	 * @return an object of PublicRepositoryInfo
	 */
	
	public PublicRepositoryInfo getPublicRepositoryInfo(JsonNode node) {
    	return  Json.fromJson(node, PublicRepositoryInfo.class);    
	}
	
	/**This method is used for deserialization. 
	 * It converts the <code>JsonNode</code> data into a 
	 * list of issues of the repository.
	 * 
	 * @author Roxane Tissier
	 * @param node JsonNode extracted from an HTTP request 
	 * @return a list of issues
	 */
	
	public List<String> getIssuesRepo(JsonNode node) {
		return node.findValues("title").stream().map(JsonNode::asText)
					.collect(Collectors.toList());
	}
	
	
	/**This method is used for deserialization. 
	 * It converts the <code>JsonNode</code> data into a list of names of
	 * the repositories.
	 * 
	 * @author Rimsha Afzal
	 * @param node JsonNode extracted from an HTTP request 
	 * @return a list of repositories' name
	 */
	
	public List<String> getOwnerRepos(JsonNode node) {
		return node.findValues("full_name").stream().map(a->{
			String b = a.asText();
			return b.split("/")[1];})
    		.collect(Collectors.toList());
	}
	
	/**This method is used for deserialization and storing users. 
	 * It converts the <code>JsonNode</code> data into an object
	 * of <code> PublicOwnerInfo</code> and maintains a record of the users
	 * that have made a search.
	 * 
	 * 
	 * @author Rimsha Afzal
	 * @param node JsonNode extracted from an HTTP request
	 * @ownerMap the map of user login and his object
	 * @searchKey owner's login
	 * @return HashMap <String, PublicOwnerInfo> a data structure storing user and his profile
	 */
	
	
	public HashMap <String, PublicOwnerInfo> insertKeyInOwnerMap(JsonNode node, HashMap <String, PublicOwnerInfo> ownerMap, String searchKey) {
		
    	PublicOwnerInfo p = Json.fromJson(node, PublicOwnerInfo.class);
    	if(!ownerMap.containsKey(searchKey)) {
    		ownerMap.put(searchKey, p);
    	}
    	
    	return ownerMap;
    
	}
	
	/**This method is used for deserialization and storing searches. 
	 * This is used to maintain user search history and results
	 * that were fetched for his request.
	 * 
	 * 
	 * @author Amrit Singh/Rimsha Afzal
	 * @param node JsonNode extracted from an HTTP request
	 * @ownerMap the map of users and his search results
	 * @searchKey the search the user made 
	 * @return user_searches a data structure storing user and his searches
	 */
	
	
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
	
	
	/**This method is used to return the frequency of words.
	 * It takes a list as an arguments and computes the frequency
	 * for all the words in the list and stores them in a <code>HashMap</code> 
	 * 
	 * @author Afif Bin Kamrul
	 * @param node JsonNode extracted from an HTTP request
	 * @ownerMap the map of users and his search results
	 * @searchKey the search the user made 
	 * @return HashMap <String, PublicOwnerInfo> a data structure storing user and his searches
	 */
	public Map<String, Integer> getIssues(List<String> s){
		Map<String, Integer> freq = s.parallelStream().flatMap(sob -> Arrays.asList(sob.split(" ")).stream()).collect(Collectors.toConcurrentMap(sob1->sob1, sob1 ->1, Integer::sum));
		return freq;
	}
	
	

}
