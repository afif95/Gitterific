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
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Guice;
import com.google.inject.Injector;

import play.libs.Json;
import javax.inject.Inject;
import utils.*;


public class OwnerActor extends AbstractActor {
	private final ActorRef ws;
	private WSClient wsc;
	String baseUrl = "https://api.github.com";
	utilClass util = new utilClass();
	HashMap<String, List<Repository>> userSearches; 
	Map <String, HashMap <String, List<Repository>>> user_searches;
	Http.Session session;
	Singleton singleton;
	String ownerName;
	
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
	
    public OwnerActor(final ActorRef wsOut, WSClient wsc, Http.Session session) {
    	ws =  wsOut;
    	this.wsc =  wsc;
    	this.userSearches = new HashMap<>();
    	this.session = session;
    	this.singleton = Singleton.getInstance( );
    	
    }

    public static Props props(final ActorRef wsout, WSClient wsc,Http.Session session) {
        return Props.create(OwnerActor.class, wsout, wsc, session);
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
			ownerName = searchVal;
			gh.fetchOwnerImp(userSearches, searchVal, ws, wsc, self(), session, singleton)
			.thenCombine(gh.fetchOwnerReposImp(userSearches, searchVal, ws, wsc, self(), session, singleton),
					(ownerInfo, ownerRepo) -> {
						final ObjectNode response = Json.newObject();
						response.put("search_flag","new" );
			            response.put("search_term", searchVal );
			            response.putPOJO("data", ownerInfo);
			            response.putPOJO("data1", ownerRepo);
						return response;
					}).thenAccept(r -> ws.tell(r, self()));
			
		} catch (Exception exp) {
		
		}

    }
    
  
    private void fetchingUpdates() {
    	
    	
		try {		
			gh.fetchOwnerImp(userSearches, ownerName, ws, wsc, self(), session, singleton)
			.thenCombine(gh.fetchOwnerReposImp(userSearches, ownerName, ws, wsc, self(), session, singleton),
					(ownerInfo, ownerRepo) -> {
						final ObjectNode response = Json.newObject();
						response.put("search_flag","new" );
			            response.put("search_term", ownerName );
			            response.putPOJO("data", ownerInfo);
			            response.putPOJO("data1", ownerRepo);
						return response;
					}).thenAccept(r -> ws.tell(r, self()));
			
		} catch (Exception exp) {
		}
			
			

		
	
        
    }
}
