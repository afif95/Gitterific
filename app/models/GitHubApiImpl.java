package models;

import java.util.ArrayList;
import play.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import actors.UserActor;
import akka.actor.ActorRef;
import dto.Repository;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http.Session;
import utils.Singleton;
import utils.utilClass;

import javax.inject.Inject;

import play.Logger;

public class GitHubApiImpl implements GitHubApi{
	utilClass util = new utilClass();
	String baseUrl = "https://api.github.com";
	Singleton singleton;
	
	/**
	 * This method is used to fetch searches from GitHub's search api.
	 * 
	 * @author Amrit/Roxane/Rimsha
	 */
	@Override
	public CompletionStage<JsonNode> fetchResultsImp(Map<String, List<Repository>> userSearches, String searchVal, ActorRef ws,
			WSClient wsc, ActorRef ua, Session session, Singleton singleton) {	
			
			return wsc.url(baseUrl + "/search/repositories?q="+ searchVal  + "&per_page=10&sort=updated")
	       
			 .get().thenApply(r -> {
				 return r.asJson();
			 });		        
	       
	}
	
	/**
	 * This method is used to fetch owner's information from GitHub's api.
	 * 
	 * @author Rimsha
	 */
	@Override
	public CompletionStage<JsonNode> fetchOwnerImp(Map<String, List<Repository>> userSearches, String searchVal, ActorRef ws,
			WSClient wsc, ActorRef ua, Session session, Singleton singleton) {	
			return wsc.url(baseUrl + "/users/"+ searchVal)
	       
			 .get().thenApply(r -> {
				 return r.asJson();
			 });		        
	       
	}
	
	/**
	 * This method is used to fetch owner's  repositories' information from GitHub's api.
	 * 
	 * @author Roxane
	 */

	@Override
	public CompletionStage<JsonNode> fetchCommitImp(Map<String, List<Repository>> userSearches, String searchVal,
			ActorRef ws, WSClient wsc, ActorRef ua, Session session, Singleton singleton) {
		return wsc.url(baseUrl + "/commit/"+ searchVal)
			       
				 .get().thenApply(r -> {
					 return r.asJson();
				 });
	}
	
	@Override
	public CompletionStage<JsonNode> fetchRepositoryImp(Map<String, List<Repository>> userSearches, String searchVal,
			ActorRef ws, WSClient wsc, ActorRef ua, Session session, Singleton singleton) {
		return wsc.url(baseUrl + "/search/repositories?q=/"+ searchVal)
			       
				 .get().thenApply(r -> {
					 return r.asJson();
				 });
	}
	
	/**
	 * This method is used to fetch owner's repositories information from GitHub's api.
	 * 
	 * @author Rimsha
	 */

	public CompletionStage<JsonNode> fetchOwnerReposImp(Map<String, List<Repository>> userSearches, String searchVal,
			ActorRef ws, WSClient wsc, ActorRef ua, Session session, Singleton singleton) {
		return wsc.url(baseUrl + "/users/"+ searchVal +  "/repos")
			       
				 .get().thenApply(r -> {
					 return r.asJson();
				 });		        
		       
	}
	
	/**
	 * This method is used to fetch topic's information from GitHub's api.
	 * 
	 * @author Amrit
	 */

	public CompletionStage<JsonNode> fetchReposByTopic(Map<String, List<Repository>> userSearches, String searchVal,
			ActorRef ws, WSClient wsc, ActorRef ua, Session session, Singleton singleton) {
			return wsc.url(baseUrl + "/search/repositories?q=topic:"+ searchVal + "&sort=updated&per_page=10")
		       
			 .get().thenApply(r -> {
				 return r.asJson();
			 });	
	}
	
	public CompletionStage<JsonNode> fetchIssueImp(Map<String, List<Repository>> userSearches, String searchVal,
			ActorRef ws, WSClient wsc, ActorRef ua, Session session, Singleton singleton) {
		return wsc.url(baseUrl + "/search/repositories?q=/"+ searchVal)
			       
				 .get().thenApply(r -> {
					 return r.asJson();
				 });
	}

/*	@Override
	public CompletionStage<WSResponse> fetchingUpdateImp(Map<String, List<Repository>> userSearches, ActorRef ws,
			WSClient wsc, Session session, Singleton singleton) {

		try {
			String sid = session.get("id").get();
			singleton.getNum(sid).keySet()
			.parallelStream()
			.forEach(searchVal -> {
			 wsc.url(baseUrl + "/search/repositories?q="+ searchVal  + "&per_page=10&sort=updated")
	        .get();		        
	        .thenApply(r -> {
	        	 String a = searchVal;
	        	 final ObjectNode response = Json.newObject();
		         final Map<String,String> a1 = new HashMap<>();
		         
		         int countNewRepos = 0;
		         List<Repository> new_search_results = util.JSONtoRepoList(r.asJson());
	        	// List<Repository> prev_repos = userSearches.get(searchVal);
		         
		         List<Repository> prev_repos = singleton.getNum(sid).get(searchVal);
	        	 List<Repository> show_new_repos = new ArrayList<>();
	        		 
	        	 
		        
		        for(Repository repo : new_search_results) {
		        	if((new_search_results.get(countNewRepos).getFull_name()).equals(prev_repos.get(0).getFull_name())) {
		        			break; 	 
		        	}	
		        	else {
		        		prev_repos.add(0,repo);	
		        	}
		        	countNewRepos++;
		        }
		        	singleton.setNum(sid,searchVal,prev_repos);
			       // userSearches.replace(searchVal,prev_repos);
		            JsonNode abc = Json.toJson(singleton.getNum(sid));
		        	//JsonNode abc = Json.toJson(prev_repos);
		            response.put("search_flag","old" );
		            response.put("search_term", searchVal );
		            response.putPOJO("data", abc);
		            return response;
       });
		});
       
			} catch (Exception exp) {
		}
		return null;	
		}*/
}
