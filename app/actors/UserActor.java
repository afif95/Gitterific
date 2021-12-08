package actors;

import scala.concurrent.duration.Duration;
import akka.actor.AbstractActorWithTimers;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import controllers.routes;
import dto.Repository;
import models.GitHubApi;
import modules.GitHubModule;
import play.Logger;
import play.data.Form;
import play.libs.ws.*;
import play.mvc.Http;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Guice;
import com.google.inject.Injector;

import play.libs.Json;
import javax.inject.Inject;
import utils.*;


/**
 * This actor is used to implement the searches.
 * @author Rimsha/Amrit
 *
 */

public class UserActor extends AbstractActor {
	private final ActorRef ws;
	private WSClient wsc;
	String baseUrl = "https://api.github.com";
	utilClass util = new utilClass();
	HashMap<String, List<Repository>> userSearches; 
	Map <String, HashMap <String, List<Repository>>> user_searches;
	Http.Session session;
	Singleton singleton;
	
	Injector injector = Guice.createInjector(new GitHubModule());
	@Inject
	private GitHubApi gh = injector.getInstance(GitHubApi.class);
	
	/**
	 * A time class that sets the time.
	 *
	 */
	
	static public class TimeMessage {
        public final String time;
        
        public TimeMessage(String time) {
            this.time = time;
            
        }
    }
	

	/**
	 * This is the declaration of the class that 
	 * is used to fetch new updates for Owner's profile
	 *
	 */
	
	
	static public class checkForUpdates {
       
    }
	
	/**
	 * This is the constructor.
	 * @param wsOut
	 * @param wsc
	 * @param session
	 */
	
	
    public UserActor(final ActorRef wsOut, WSClient wsc, Http.Session session) {
    	ws =  wsOut;
    	this.wsc =  wsc;
    	this.userSearches = new HashMap<>();
    	this.session = session;
    	this.singleton = Singleton.getInstance( );
    	
    	
    }
    
    /**
     * These are the props.
     * @param wsout
     * @param wsc
     * @param session
     * @return
     */
    
    public static Props props(final ActorRef wsout, WSClient wsc,Http.Session session) {
        return Props.create(UserActor.class, wsout, wsc, session);
    }
    

    /**
     * This method decides which method will be called based
     * on different behaviours. It will either fetch information for
     * the first time or send updates for the existing one.
     */
    
    @Override 
    public Receive createReceive() {
    	return receiveBuilder().match(TimeMessage.class, this::sendTime)
    						   .match(checkForUpdates.class, a -> fetchingUpdates())
    						   .match(ObjectNode.class, this::fetchResults)
    						   //.matchAny(o -> fetchResults(o))
    						   .build();
    }
    
    /**
     * This method is used to register actor in the timer Actor
     * so that updates can be send after a set duration
     */
    
    @Override
    public void preStart() {
       	context().actorSelection("/user/timeActor/")
                 .tell(new TimeActor.RegisterMsg(), self());
    }
    
    
   

    /**
     * This method is used to test the behaviour of sockets.
     * @param msg
     */
   
    
    private void sendTime(TimeMessage msg) {
        final ObjectNode response = Json.newObject();
        final Map<String,String> a1 = new HashMap<>();
        a1.put("1","hello");
        JsonNode abc = Json.toJson(a1);
        response.putPOJO("time", abc);
        //response.put("time", abc);
        ws.tell(response, self());
    }
    
    /**
     * This method fetches the search for the first time. 
     * for the first time and sends it through the socket.
     * @param js
     */
    
    private void fetchResults(JsonNode js){
    	
		try {
			String sid = session.get("id").get();
			String searchVal = js.get("message").textValue();
			gh.fetchResultsImp(userSearches, searchVal, ws, wsc, self(), session, singleton)
			 .thenApply(r -> {
		            final ObjectNode response = Json.newObject();
		            final Map<String,String> a1 = new HashMap<>();
		            
		            List<Repository> repos = util.JSONtoRepoList(r);
		            singleton.setNum(sid, searchVal,repos);
		            JsonNode abc = Json.toJson(repos);
		            
		            
		            response.put("search_flag","new" );
		            response.put("search_term", searchVal );
		            response.putPOJO("data", abc);
		            return response;
		        }).thenAccept(r -> ws.tell(r, self()));
        	
		} catch (Exception exp) {
		
		}

    }
    
    /**
     * This method is used to fetch updates for searches' page.
     * This will be called for the timer class.
     */
  
    private void fetchingUpdates() {
    	
    	
		try {		
			
			String sid = session.get("id").get();

			singleton.getNum(sid).keySet()
			.parallelStream()
			.forEach(searchVal -> {
			gh.fetchResultsImp(userSearches, searchVal, ws, wsc, self(), session, singleton)		        
	        .thenApply(r -> {
	        	 String a = searchVal;
	        	 final ObjectNode response = Json.newObject();
		         final Map<String,String> a1 = new HashMap<>();
		         
		         int countNewRepos = 0;
		         List<Repository> new_search_results = util.JSONtoRepoList(r);
	        	// List<Repository> prev_repos = userSearches.get(searchVal);
		         
		         List<Repository> prev_repos = singleton.getNum(sid).get(searchVal);
	        	 List<Repository> show_new_repos = new ArrayList<>();
	        		 
	        	 
		        
		        for(Repository repo : new_search_results) {
		        	if((new_search_results.get(countNewRepos).getFull_name()).equals(prev_repos.get(0).getFull_name())) {
		        			break; 	 
		        	}	
		        	else {
		        		show_new_repos.add(repo);	
		        	}
		        	countNewRepos++;
		        }
		        	prev_repos.addAll(0, show_new_repos);
		        	prev_repos = prev_repos.stream().distinct().collect(Collectors.toList());
		        	singleton.setNum(sid,searchVal,prev_repos);
			       // userSearches.replace(searchVal,prev_repos);
		            JsonNode abc = Json.toJson(singleton.getNum(sid));
		        	//JsonNode abc = Json.toJson(prev_repos);
		            response.put("search_flag","old" );
		            response.put("search_term", searchVal );
		            response.putPOJO("data", abc);
		            return response;
       }).thenAccept(r -> ws.tell(r, self()));
		});
       
			
			//ObjectNode response = gh.fetchingUpdateImp(userSearches, ws, wsc, session, singleton);
			//ws.tell(response, self());
			
		} catch (Exception exp) {
		}
			
			

		
	
        
    }
}
