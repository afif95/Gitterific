package controllers;

import play.mvc.*;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import play.libs.streams.ActorFlow;
import actors.OwnerActor;
import actors.RepositoryActor;
import actors.TimeActor;
import actors.TopicsActor;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import views.package$;
import actors.UserActor;
import play.libs.ws.*;
import dto.*;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
	
	
	@Inject private ActorSystem actorSystem;
	@Inject private Materializer materializer; 
	@Inject private WSClient wsc;
	private Integer id = 0;
	public HashMap <String, HashMap <String, List<Repository>>> user_searches;
	
	/**
	 * Constructor that initializes the time actor.
	 * @param system
	 */
	
	@Inject
	public HomeController(ActorSystem system) {
		system.actorOf(TimeActor.getProps(),"timeActor");
		user_searches = new HashMap<>();
	}
	
	/**
     * An action that renders an HTML page with search form.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>. THe request 
     * is binded to the session
     * 
     * @param request
     * @author Rimsha/Amrit
     */
	
	
	public Result index(Http.Request request) {
		String uid;
		//String url = routes.HomeController.ws().webSocketURL(request);
		if((request.session().get("id")).isPresent()) {
			
			return ok(views.html.index.render());
		}
		else {
			id=id+1;
	    	uid = id.toString();
			return ok(views.html.index.render()).addingToSession(request, "id",uid);
        }
    }
	
	
	/**
     * An action that renders an HTML page with owner profile.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/owner</code>. THe request 
     * is binded to the session
     * 
     * @param ownerName the owner whose profile is to be fetched
     * @author Rimsha
     */
	
	public Result owner(String ownerName) {
		String uid;
		//String url = routes.HomeController.ws().webSocketURL(request);
		//if((request.session().get("id")).isPresent()) {
			
			return ok(views.html.owner.render(ownerName));
		//}
		
    }
	
	
	/**
     * An action that renders an HTML page for topics.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/topics</code>. THe request 
     * is binded to the session
     * 
     * @param topic the topic of interest
     * @author Amrit
     */
	
	public Result topics(String topic) {
		String uid;
		//String url = routes.HomeController.ws().webSocketURL(request);
		//if((request.session().get("id")).isPresent()) {
			
			return ok(views.html.topics.render(topic));
		//}
		
    }
	
/*	public Result getOwner(Http.Request request) {
		String uid;
		//String url = routes.HomeController.ownerWS().webSocketURL(request);
		if((request.session().get("id")).isPresent()) {
			
			return ok(views.html.index.render(url));
		}
		else {
			id=id+1;
	    	uid = id.toString();
			return ok(views.html.index.render(url)).addingToSession(request, "id",uid);
        }
    }*/
	
	/**
     * An action that renders an HTML page with owner profile.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/repository</code>. THe request 
     * is binded to the session
     * 
     * @param Owner owner of the repository
     * @param repository the full name of repository
     * @author Roxane
     */
	
	public Result repository(String Owner, String repository) {
		String uid;
		return ok(views.html.repository.render(Owner, repository));
		
    }
   
	/**
     * Websocket used to send the search and search results 
     * handled by the <code>User Actor</code>
     * 
     * @author Rimsha/Amrit
     */
	
    public WebSocket ws() {
        return WebSocket.Json.accept(request -> ActorFlow.actorRef(
				ws -> UserActor.props(ws,wsc,request.session()), actorSystem, materializer));
    }
    
    
    /**
     * Websocket used to send the owner progile information 
     * handled by the <code>Owner Actor</code>
     * 
     * @author Rimsha
     */
    
    public WebSocket ownerWS() {
        return WebSocket.Json.accept(request -> ActorFlow.actorRef(
				ws -> OwnerActor.props(ws,wsc,request.session()), actorSystem, materializer));
    }
    
    /**
     * Websocket used to send the repository profile 
     * handled by the <code>Repository Actor</code>
     * 
     * @author Roxane
     */

    public WebSocket repositoryWS() {
        return WebSocket.Json.accept(request -> ActorFlow.actorRef(
				ws -> RepositoryActor.props(ws,wsc,request.session()), actorSystem, materializer));
    }
    
    /**
     * Websocket used to send the topic's profile page
     * handled by the <code>Topics Actor</code>
     * 
     * @author Amrit
     */

    public WebSocket topicWS() {
        return WebSocket.Json.accept(request -> ActorFlow.actorRef(
				ws -> TopicsActor.props(ws,wsc,request.session()), actorSystem, materializer));

    }
}
