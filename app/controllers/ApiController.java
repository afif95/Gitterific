package controllers;

import play.mvc.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Strings;
import com.typesafe.config.ConfigFactory;

import dto.RequestInputSearch;
import dto.formData;
import play.libs.ws.*;
import views.html.*;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import play.routing.RoutingDsl;
import play.server.Server;
import play.api.data.Form;
import play.data.FormFactory;
import play.data.FormFactory.*;
import play.i18n.MessagesApi;
import play.libs.Json;

import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;
/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class ApiController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
	
	@Inject
	FormFactory formFactory;
	
	private WSClient ws;
	private play.data.Form<formData> form;
    private MessagesApi messagesApi;

    
	  @Inject
	  public ApiController(WSClient ws,FormFactory formFactory, MessagesApi messagesApi) {
	    this.ws = ws;
	    this.form=formFactory.form(formData.class);
	    this.messagesApi= messagesApi;
	    
	  }
	  
	  public Result index1(){
	     
	        return ok(views.html.index.render());
	    }
	  
	  public Result home(Http.Request request) {
	        return ok(views.html.home.render(formFactory.form(formData.class), messagesApi.preferred(request)));
	   }
	    
	  
	  
	  public Result save(Http.Request request) {
		  
	        form = formFactory.form(formData.class).bindFromRequest(request);
	        if(form.hasErrors()){
	           
	            return badRequest(views.html.index.render());
	        }
	        formData fd = form.get();
		  return redirect(routes.ApiController.searchQuery(fd.searchInput));
	        
	  }
	  
	  String baseUrl = "https://api.github.com";

	  public CompletionStage<Result> searchQuery(String searchKey) {
	    return ws.url(baseUrl + "/search/repositories?q="+ searchKey + "&per_page=5")
	        .get()
	        .thenApplyAsync(result -> {
	        		
	        	JsonNode jd =  result.asJson().findValue("items");
	        	List<RequestInputSearch> repo = new ArrayList<>(); 
	        	for(JsonNode a : jd) {
	        		RequestInputSearch ris = Json.fromJson(a, RequestInputSearch.class);
	        		repo.add(ris);
	        	}
	        	//return redirect(routes.ApiController.index1());
	        	return ok(Json.toJson(repo));
	        	});
	        	
	    }
	        
}
