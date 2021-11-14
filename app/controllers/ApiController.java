package controllers;

import play.mvc.*;
import play.mvc.Http.Request;
import play.mvc.Http.Session;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Strings;
import com.typesafe.config.ConfigFactory;

import dto.Repository;
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
import play.data.Form;
import play.data.FormFactory;
import play.data.FormFactory.*;
import play.i18n.MessagesApi;
import play.libs.Json;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
	private Form<formData> form;
    private MessagesApi messagesApi;
	private List<Repository> repos;
	String baseUrl = "https://api.github.com";



    
	  @Inject
	  public ApiController(WSClient ws,FormFactory formFactory, MessagesApi messagesApi) {
	    this.ws = ws;
	    this.form=formFactory.form(formData.class);
	    this.messagesApi= messagesApi;
	    repos = new ArrayList<>();
	    
	  }
	  
	  public Result index1(){
	     
	        return ok(views.html.index.render());
	    }
	  
	/*  public Result home(Request request) {
		  List<Repository> repoList = Repository.getRepos();
	        return ok(views.html.home.render(formFactory.form(formData.class), repoList, messagesApi.preferred(request)));
	   }*/
	  
	  public CompletionStage<Result> showRepos(Request request) {
			return CompletableFuture.completedFuture(ok(views.html.home.render(form, repos, request, messagesApi.preferred(request))));
		}
	  
		public CompletionStage<Result> fetchRepos(Request request) {
			final Form<formData> bindedForm = form.bindFromRequest(request);

			if (bindedForm.hasErrors()) {
				return CompletableFuture.completedFuture(badRequest(views.html.home.render(bindedForm, repos, request, messagesApi.preferred(request))));
			} else {
				//return CompletableFuture.supplyAsync(() -> {
				formData data = bindedForm.get();
				//Session session = new Session();
				//session.adding(data.searchInput, "session");
		

				try {
					
					String searchVal = data.searchInput;
					
					return ws.url(baseUrl + "/search/repositories?q="+ searchVal  + "&per_page=5")
			        .get()
			        
			        .thenApply(r -> {
			        				        	
			        	List<Repository> list = StreamSupport.stream( r.asJson().get("items").spliterator(), false)
			                    .map(sObj -> convertToRepo(sObj))
			                    .collect(Collectors.toList());
			        	
			        	//repos.addAll(list); 
			        	
			        	/*JsonNode jd =  r.asJson().findValue("items");
			        	List<Repository> repo = new ArrayList<>(); 
			        	for(JsonNode a : jd) {
			        		Repository ris = Json.fromJson(a, Repository.class);
			        		repo.add(ris);
			        	} 

			        	repos.addAll(repo); */
			  		    //List<Repository> repoList = Repository.getRepos();
			        	return list;
			        }).thenApply(list -> {
			        	repos.addAll(list);
			        	return repos;
			        }).thenApply(m -> redirect(routes.ApiController.showRepos()));
			        		
				} catch (Exception exp) {
					return CompletableFuture.completedFuture(ok());
				}
				
				

			}
		}
	    
	  
	  
	/*  public Result save(Request request) {
		  
	        form = formFactory.form(formData.class).bindFromRequest(request);
	        if(form.hasErrors()){
	           
	            return badRequest(views.html.index.render());
	        }
	        formData fd = form.get();
	        
	        
	        searchQuery(fd.searchInput, request);
		  //List<Repository> repoList = Repository.getRepos();
		  //return redirect(routes.ApiController.searchQuery(fd.searchInput, request));
	        return redirect(routes.ApiController.home());
	        
	  }*/
	  

	/*  public CompletionStage<Result> searchQuery(String searchKey, Request request) {
	    return ws.url(baseUrl + "/search/repositories?q="+ searchKey + "&per_page=5")
	        .get()
	        .thenApply(result -> {
	        	
	        	List<Repository> list = StreamSupport.stream( result.asJson().get("items").spliterator(), true)
	                    .map(sObj -> convertToRepo(sObj))
	                    .collect(Collectors.toList());
	        	
	        	Repository.addAllRepo(list);

	  		    List<Repository> repoList = Repository.getRepos();

	  		    //return redirect(routes.ApiController.home());
	  		    //return redirect("/home");
	  		    //return redirect(routes.ApiController.home());
	        	return ok(views.html.home.render(formFactory.form(formData.class), repoList, messagesApi.preferred(request)));
	        		
	        	
	    
	        	});
	    

	        	
	    } */
	  
	  public Repository convertToRepo(JsonNode str) {
		  return Json.fromJson(str, Repository.class);
	  }
	  

	  
	/*   public CompletionStage<Repository> getRepos(final String keywords) {
           return ws.url(baseUrl + "/search/repositories?q="+ keywords + "&per_page=5")
       	        .get()
                   .thenApplyAsync(WSResponse::asJson)
                   .thenApplyAsync(this::convertToRepo);
   } */
	        
}
