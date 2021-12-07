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
	
	@Inject
	public HomeController(ActorSystem system) {
		system.actorOf(TimeActor.getProps(),"timeActor");
		user_searches = new HashMap<>();
	}
	
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
	
	public Result owner(String ownerName) {
		String uid;
		//String url = routes.HomeController.ws().webSocketURL(request);
		//if((request.session().get("id")).isPresent()) {
			
			return ok(views.html.owner.render(ownerName));
		//}
		
    }
	
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
	
	public Result repository(String Owner, String repository) {
		String uid;
		return ok(views.html.repository.render(Owner, repository));
		
    }
   
    public WebSocket ws() {
        return WebSocket.Json.accept(request -> ActorFlow.actorRef(
				ws -> UserActor.props(ws,wsc,request.session()), actorSystem, materializer));
    }
    
    public WebSocket ownerWS() {
        return WebSocket.Json.accept(request -> ActorFlow.actorRef(
				ws -> OwnerActor.props(ws,wsc,request.session()), actorSystem, materializer));
    }
    

    public WebSocket repositoryWS() {
        return WebSocket.Json.accept(request -> ActorFlow.actorRef(
				ws -> RepositoryActor.props(ws,wsc,request.session()), actorSystem, materializer));
    }

    public WebSocket topicWS() {
        return WebSocket.Json.accept(request -> ActorFlow.actorRef(
				ws -> TopicsActor.props(ws,wsc,request.session()), actorSystem, materializer));

    }
}
