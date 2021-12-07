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
	
	
	static public class TimeMessage {
        public final String time;
        
        public TimeMessage(String time) {
            this.time = time;
            
        }
    }
	
	static public class checkForUpdates {
       
    }
	
    public TopicsActor(final ActorRef wsOut, WSClient wsc, Http.Session session) {
    	ws =  wsOut;
    	this.wsc =  wsc;
    	this.userSearches = new HashMap<>();
    	this.session = session;
    	this.singleton = Singleton.getInstance( );
    	
    }

    public static Props props(final ActorRef wsout, WSClient wsc,Http.Session session) {
        return Props.create(TopicsActor.class, wsout, wsc, session);
    }
    
    @Override 
    public Receive createReceive() {
    	return receiveBuilder().match(TimeMessage.class, this::sendTime)
    						   .match(checkForUpdates.class, a -> fetchingUpdates())
    						   .match(ObjectNode.class, this::fetchResults)
    						   //.matchAny(o -> fetchResults(o))
    						   .build();
    }
    
    @Override
    public void preStart() {
       	context().actorSelection("/user/timeActor/")
                 .tell(new TimeActor.RegisterMsg(), self());
    }
    
    
   
   
    
    private void sendTime(TimeMessage msg) {
        final ObjectNode response = Json.newObject();
        final Map<String,String> a1 = new HashMap<>();
        a1.put("1","hello");
        JsonNode abc = Json.toJson(a1);
        response.putPOJO("time", abc);
        //response.put("time", abc);
        ws.tell(response, self());
    }
    
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
    
  
    private void fetchingUpdates() {
    	
    	
		try {		
			gh.fetchReposByTopic(userSearches, topic, ws, wsc, self(), session, singleton)
			 .thenApply(r -> {
		            final ObjectNode response = Json.newObject();
		            final Map<String,String> a1 = new HashMap<>();
		            
		            List<Repository> repos = util.JSONtoRepoList(r);
		           // singleton.setNum(sid, searchVal,repos);
		            JsonNode abc = Json.toJson(repos);
		            user_searches.addAll(0, repos);
		            user_searches = user_searches.stream().distinct().collect(Collectors.toList());
		            
		            response.put("search_flag","old" );
		            response.put("search_term", topic );
		            response.putPOJO("data", abc);
		            return response;
		        }).thenAccept(r -> ws.tell(r, self()));
		} catch (Exception exp) {
		}
			
			

		
	
        
    }
}
