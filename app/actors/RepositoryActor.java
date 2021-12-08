package actors;

import scala.concurrent.duration.Duration;
import akka.actor.AbstractActorWithTimers;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.AbstractActor.Receive;
import controllers.routes;
import dto.RepositoryInfo;
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

import actors.OwnerActor.TimeMessage;
import actors.OwnerActor.checkForUpdates;
import play.libs.Json;
import javax.inject.Inject;
import utils.*;

/**
 * This actor is used to implement the repositories' information page.
 * @author Roxane
 *
 */

public class RepositoryActor extends AbstractActor {
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
	 * This class is used to fetch new updates for Repository
	 *
	 */
	
	static public class checkForUpdates {
       
    }
	


	/**
	 * This is the constructor.
	 */

    public RepositoryActor(final ActorRef wsOut, WSClient wsc, Http.Session session) {
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
        return Props.create(RepositoryActor.class, wsout, wsc, session);
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
     * This method fetches the repository information for the first time
     * and sends it through the socket.
     * @param js
     */
    
    private void fetchResults(JsonNode js){
    	
		try {
			String sid = session.get("id").get();
			String searchVal = js.get("message").textValue();
			gh.fetchRepositoryImp(userSearches, searchVal, ws, wsc, self(), session, singleton)
			 .thenApply(r -> {
		            final ObjectNode response = Json.newObject();
		            final Map<String,String> a1 = new HashMap<>();
		            //Logger.info(r.get("items").toString());
		            RepositoryInfo p = Json.fromJson(r, RepositoryInfo.class);
		           
		            JsonNode abc = r;
		            
		            
		            response.put("search_flag","new" );
		            response.put("search_term", searchVal );
		            response.putPOJO("data", r.get("items"));
		            return response;
		        }).thenAccept(r -> ws.tell(r, self()));
        	
		} catch (Exception exp) {
		
		}

    }
    
    /**
     * This method is used to fetch updates for repository's information.
     * This will be called for the timer class.
     */
    
  
    private void fetchingUpdates() {
    	
    	
		try {		
			/*
			String sid = session.get("id").get();
			//String searchVal = js.get("message").textValue();
			gh.fetchOwnerImp(userSearches, searchVal, ws, wsc, self(), session, singleton)
			 .thenApply(r -> {
		            final ObjectNode response = Json.newObject();
		            final Map<String,String> a1 = new HashMap<>();
		            
		            PublicOwnerInfo p = Json.fromJson(r, PublicOwnerInfo.class);
		           // List<Repository> repos = util.JSONtoRepoList(r);
		           // singleton.setNum(sid, searchVal,repos);
		            JsonNode abc = r;
		            
		            
		            response.put("search_flag","new" );
		            response.put("search_term", searchVal );
		            response.putPOJO("data", r);
		            return response;
		        }).thenAccept(r -> ws.tell(r, self()));
			
			//ObjectNode response = gh.fetchingUpdateImp(userSearches, ws, wsc, session, singleton);
			//ws.tell(response, self());
			*/
		} catch (Exception exp) {
		}
			
			

		
	
        
    }
}
