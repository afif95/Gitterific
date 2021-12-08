package actors;

import scala.concurrent.duration.Duration;
import akka.actor.AbstractActorWithTimers;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import controllers.routes;
import dto.PublicOwnerInfo;
import dto.Repository;
import models.GitHubApi;
import modules.GitHubModule;
import play.Logger;
import play.data.Form;
import play.libs.ws.*;
import play.mvc.Http;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
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
 * This actor is used to implement the topic's page.
 * @author Amrit
 *
 */

public class TopicsActor extends AbstractActor {
	private final ActorRef ws;
	private WSClient wsc;
	String baseUrl = "https://api.github.com";
	utilClass util = new utilClass();
	HashMap<String, List<Repository>> userSearches; 
	List<Repository> user_searches;
	Http.Session session;
	Singleton singleton;
	String topic;
	
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
	
    public TopicsActor(final ActorRef wsOut, WSClient wsc, Http.Session session) {
    	ws =  wsOut;
    	this.wsc =  wsc;
    	user_searches = new ArrayList<>();
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
        return Props.create(TopicsActor.class, wsout, wsc, session);
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
     * This method fetches the information for topics page 
     * for the first time and sends it through the socket.
     * @param js
     */
    
    
    private void fetchResults(JsonNode js){
    	
		try {
			String sid = session.get("id").get();
			String searchVal = js.get("message").textValue();
	    	this.topic = searchVal;

			gh.fetchReposByTopic(userSearches, searchVal, ws, wsc, self(), session, singleton)
			 .thenApply(r -> {
		            final ObjectNode response = Json.newObject();
		            final Map<String,String> a1 = new HashMap<>();
		            
		            List<Repository> repos = util.JSONtoRepoList(r);
		           // singleton.setNum(sid, searchVal,repos);
		            JsonNode abc = Json.toJson(repos);
		            user_searches.addAll(repos);
		            
		            response.put("search_flag","new" );
		            response.put("search_term", searchVal );
		            response.putPOJO("data", abc);
		            return response;
		        }).thenAccept(r -> ws.tell(r, self()));
        	
		} catch (Exception exp) {
		
		}

    }
    
    /**
     * This method is used to fetch updates for topic's page.
     * This will be called for the timer class.
     */
  
    private void fetchingUpdates() {
    	
    	
		try {		
			gh.fetchReposByTopic(userSearches, topic, ws, wsc, self(), session, singleton)
			 .thenApply(r -> {
		            final ObjectNode response = Json.newObject();
		            final Map<String,String> a1 = new HashMap<>();
		            
		            List<Repository> repos = util.JSONtoRepoList(r);
		           // singleton.setNum(sid, searchVal,repos);
		            
		        	 List<Repository> show_new_repos = new ArrayList<>();
		        	 int countNewRepos = 0;
			        
			        for(Repository repo : repos) {
			        	if((repos.get(countNewRepos).getFull_name()).equals(user_searches.get(0).getFull_name())) {
			        			break; 	 
			        	}	
			        	else {
			        		show_new_repos.add(repo);	
			        	}
			        	countNewRepos++;
			        }
			        user_searches.addAll(0, show_new_repos);
			        user_searches = user_searches.stream().distinct().collect(Collectors.toList());
		            
		            //repos = repos.stream().filter(r2 -> !user_searches.contains(r2)).collect(Collectors.toList());
		           // user_searches.addAll(0, repos);
		            //user_searches = user_searches.stream().filter(r -> user)
		            JsonNode abc = Json.toJson(user_searches);

		            response.put("search_flag","old" );
		            response.put("search_term", topic );
		            response.putPOJO("data", abc);
		            return response;
		        }).thenAccept(r -> ws.tell(r, self()));
		} catch (Exception exp) {
		}	
			
			

		
	
        
    }
}
