package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import actors.UserActor;
import akka.actor.ActorRef;
import dto.Repository;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http.Session;
import utils.Singleton;
import utils.utilClass;

import javax.inject.Inject;
import play.Logger;



public class GitHubApiMock implements GitHubApi {

	utilClass util = new utilClass();
	String jsonForFetch = "[{\"owner\" : {\"login\" : \"User\", \"id\" : 1}, \"full_name\" : \"login\", \"topics\" : [\"topic\"]}]";
	String jsonStr = "{\"items\" : [{\"owner\" : {\"login\" : \"User\", \"id\" : 1}, \"full_name\" : \"login\", \"topics\" : [\"topic\"]}]}";
	String jsonStr2 = "{\"items\" : [{\"owner\" : {\"login\" : \"User\", \"id\" : 1}, \"full_name\" : \"login\", \"topics\" : [\"topic\"]},{\"owner\" : {\"login\" : \"User2\", \"id\" : 2}, \"full_name\" : \"full_name2\", \"topics\" : [\"topic2\"]}]}";
	String jsonTopics = "{\"items\" : [{\"owner\" : {\"login\" : \"User\", \"id\" : 1}, \"full_name\" : \"login\", \"topics\" : [\"java\"]},{\"owner\" : {\"login\" : \"User2\", \"id\" : 2}, \"full_name\" : \"full_name2\", \"topics\" : [\"java\"]}]}";
	

	String jsonRepo = "{\"login\" : \"User\" , \"id\" : 1, \"node_id\" : \"NJueibhcOIJB875DCBNdcsj\"}";
;
	String jsonOwnerRepos;
	
	public ObjectNode createResponse(String searchVal, JsonNode str) {
        final ObjectNode response = Json.newObject();
		response.put("search_flag","new" );
        response.put("search_term", searchVal );
        response.putPOJO("data", str);
		
        return response;
	}
	
	
	@Override
	public CompletionStage<JsonNode> fetchResultsImp(Map<String, List<Repository>> userSearches, String searchVal, ActorRef ws,
			WSClient wsc, ActorRef ua, Session session, Singleton singleton) {
		return CompletableFuture.supplyAsync(() -> {
			List<Repository> repos = util.JSONtoRepoList(util.createJson(jsonStr));
	        userSearches.putIfAbsent(searchVal,repos);
	        final ObjectNode response = createResponse(searchVal, util.createJson(jsonForFetch));
	        return response;
		});
		
	
	}
	
	@Override
	public CompletionStage<JsonNode> fetchOwnerImp(Map<String, List<Repository>> userSearches, String searchVal,
			ActorRef ws, WSClient wsc, ActorRef ua, Session session, Singleton singleton) {
		return CompletableFuture.supplyAsync(() -> {
			List<Repository> repos = util.JSONtoRepoList(util.createJson(jsonStr));
	        userSearches.putIfAbsent(searchVal,repos);
	        final ObjectNode response = createResponse(searchVal, util.createJson(jsonForFetch));
	        return response;
		});
	}


	@Override
	public CompletionStage<JsonNode> fetchRepositoryImp(Map<String, List<Repository>> userSearches, String searchVal,
			ActorRef ws, WSClient wsc, ActorRef ua, Session session, Singleton singleton){
		Logger.info(jsonRepo);

		return CompletableFuture.supplyAsync(() -> {
			List<Repository> repos = util.JSONtoRepoList(util.createJson(jsonRepo));
	        userSearches.putIfAbsent(searchVal,repos);
	        final ObjectNode response = createResponse(searchVal, util.createJson(jsonRepo));
	        return response;
		});
	}
	
	
		public CompletionStage<JsonNode> fetchOwnerReposImp(Map<String, List<Repository>> userSearches, String searchVal,
			ActorRef ws, WSClient wsc, ActorRef ua, Session session, Singleton singleton) {
			return CompletableFuture.supplyAsync(() -> {
				List<Repository> repos = util.JSONtoRepoList(util.createJson(jsonStr));
		        userSearches.putIfAbsent(searchVal,repos);
		        final ObjectNode response = createResponse(searchVal, util.createJson(jsonForFetch));
		        return response;
			});
	}
	


	/*public ObjectNode createUpdatedResponse(Map<String,List<Repository>> userSearches, String searchVal) {
		int countNewRepos = 0;
		
		List<Repository> new_search_results = util.JSONtoRepoList(util.createJson(jsonStr2));
		
		List<Repository> prev_repos = userSearches.get(searchVal);
   	 	List<Repository> show_new_repos = new ArrayList<>();
   	 	for(Repository repo : new_search_results) {
   	 	Logger.info(repo.getFull_name());
   	 	Logger.info(prev_repos.get(0).getFull_name());
   	 		if((new_search_results.get(countNewRepos).getFull_name()).equals(prev_repos.get(0).getFull_name())) {
     			//break; 	 
   	 		}	
	     	else {
	     		show_new_repos.add(repo);	
	     	}
   	 		countNewRepos++;
   	 	}
   	 
   	 	
   	 	if(show_new_repos.size()>0) {
   	 		userSearches.replace(searchVal,show_new_repos);
   	 		final ObjectNode response = createResponse(searchVal, Json.toJson(show_new_repos));
   	 		return response;
   	 	}
   	 	return null;
	}*/



	@Override
	public CompletionStage<JsonNode> fetchReposByTopic(Map<String, List<Repository>> userSearches, String searchVal,
			ActorRef ws, WSClient wsc, ActorRef ua, Session session, Singleton singleton) {
		// TODO Auto-generated method stub
		return CompletableFuture.supplyAsync(() -> {
			//List<Repository> repos = util.JSONtoRepoList(util.createJson(jsonTopics));
	        //userSearches.putIfAbsent(searchVal,repos);
	        final ObjectNode response = createResponse(searchVal, util.createJson(jsonTopics));
	        return response;
		});
	}
	
	
	
	
	/*
	@Override
	public ObjectNode fetchingUpdateImp(JsonNode r,  Map<String, List<Repository>> userSearches, String searchVal) {
		final ObjectNode response = Json.newObject();
	       final Map<String,String> a1 = new HashMap<>();
	            
	       List<Repository> repos = util.JSONtoRepoList(r);
	       userSearches.putIfAbsent(searchVal,repos);
	       JsonNode abc = Json.toJson(repos);
	            
	       response.put("search_flag","new" );
	       response.put("search_term", searchVal );
	       response.putPOJO("data", abc);
	        	
	       return response;
	}

	@Override
	public ObjectNode fetchResultsImp(JsonNode r, Map<String, List<Repository>> userSearches, String searchVal) {
		// TODO Auto-generated method stub
		return null;
	}
*/
}
