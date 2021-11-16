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
import dto.PublicOwnerInfo;
import dto.PublicRepositoryInfo;
import play.libs.ws.*;
import views.html.*;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
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
	private Integer id = 0;
	private HashMap <String, List<Repository>> user_searches;
	private HashMap <String, List<Repository>> all_searches;
	private HashMap <String, PublicOwnerInfo> ownerMap;
	String baseUrl = "https://api.github.com";

		

	  /**
	   * Class constructor
	   * @param ws an object of WSClient
	   * @param formFactory an object of FormFactory
	   * @param messagesApi an object of MessagesApi
	   */

    
	  @Inject
	  public ApiController(WSClient ws,FormFactory formFactory, MessagesApi messagesApi) {
	    this.ws = ws;
	    this.form=formFactory.form(formData.class);
	    this.messagesApi= messagesApi;
	    this.repos = new ArrayList<>();
	    //this.id = id;
	    this.user_searches = new HashMap<>();
	    this.all_searches = new HashMap<>();
	    this.ownerMap= new HashMap<>();
	    
	  }
	  
	  public Result index1(){
	     
	        return ok(views.html.index.render());
	    }
	  

	  
	  /**
	   * An action that renders an HTML page with search bar and search results for GitHub.
	   * The configuration in the <code>routes</code> file means that
	   * this method will be called when the application receives a
	   * <code>GET</code> request with a path of <code>/home</code>. 
	   * The method adds a key value pair to the request to identify 
	   * different sessions if one doesn't exists before.
	   * Finally, <code>CompletionStage<Result><code> is returned.
	   * 
	   * @author Amrit/Rimsha
	   * @param request
	   * @return CompletionStage<Result>
	   */
	  
	  public CompletionStage<Result> showRepos(Request request) {
		  	String uid;
		  	 if((request.session().get("id")).isPresent()) {
		  		 return CompletableFuture.completedFuture(ok(views.html.home.render(form, user_searches.get(request.session().get("id").get()), request, messagesApi.preferred(request))));
		  	 }
		  	 else {
		  		id=id+1;
		    	uid = id.toString();
		    	List<Repository> repo = new ArrayList<>();
		    	
		    	user_searches.put(uid, repo);
		        return CompletableFuture.completedFuture(ok(views.html.home.render(form, repo, request, messagesApi.preferred(request))).addingToSession(request, "id",uid));
		  	 }
		}
	  
	  
	  
	  /**
	   * An action that renders an HTML page with search bar and search results for GitHub.
	   * The configuration in the <code>views.home.scala</code> file means that
	   * this method will be called when the application receives a
	   * search request for GitHub repositories. 
	   * The method hits the GitHub api and processes the results asynchronously.
	   * The <code>json<code> from the api is converted into a java object using streams.
	   * Finally, <code>CompletionStage<Result><code> is returned.
	   * 
	   * @author Amrit/Rimsha
	   * @param request a request that has search parameter
	   * @return CompletionStage<Result>
	   */
	  
	  
		public CompletionStage<Result> fetchRepos(Request request) {
			final Form<formData> bindedForm = form.bindFromRequest(request);

			if (bindedForm.hasErrors()) {
				return CompletableFuture.completedFuture(badRequest(views.html.home.render(bindedForm, repos, request, messagesApi.preferred(request))));
			} else {
				
				formData data = bindedForm.get();
				

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
			        	String s = request.session().get("id").get();
			        	if(!all_searches.containsKey(searchVal)){
			        		all_searches.put(searchVal, list);
			        	}
			        	/*else {
			        		all_searches.put(searchVal, list);
			        	}*/

			        	//repos.addAll(list);
			        	user_searches.get(s).addAll(list);
			        	return user_searches.get(s);
			        }).thenApply(m -> redirect(routes.ApiController.showRepos()));
			        		
				} catch (Exception exp) {
					return CompletableFuture.completedFuture(ok());
				}
				
				

			}
		}
	    
	  
	  
		/**
		   * This method converts JsonNode into an object of <code>Repository class<code>
		   * @author Amrit
		   * @param str 
		   * @return Respository object of Repository that stores respository's information
		   */
		
	  public static Repository convertToRepo(JsonNode str) {
		  return Json.fromJson(str, Repository.class);
	  }
	  
	  /**
	   * An action that redirects to <code>getOwnerRepos<code> method.
	   * This will be called when a user clicks on the owner's name on the
	   * search results page.
	   * This method gets all the public information of an owner and binds it
	   * to an object of <code>PublicOwnerInfo class<code>
	   * 
	   * @author Rimsha
	   * @param searchKey the login of the owner 
	   * @return CompletionStage<Result>
	   */
	  
	  public CompletionStage<Result> getOwner(String searchKey) {
		  return ws.url(baseUrl + "/users/"+ searchKey)
			        .get()
			        .thenApplyAsync(result -> {	
			        	JsonNode jd =  result.asJson();
			        	PublicOwnerInfo p = Json.fromJson(jd, PublicOwnerInfo.class);
			        	if(!ownerMap.containsKey(searchKey)) {
			        		ownerMap.put(searchKey, p);
			        	}
			        	return redirect(routes.ApiController.getOwnerRepos(searchKey));
			        });
		  
	  }
	  
	  /**
	   * An action that returns the Owner's Profile Page.
	   * This will be called from <code>getOwner<code> method to fetch
	   * the name of the given owner's public repositories.
	   * 
	   * @author Rimsha
	   * @param searchKey the login of the owner 
	   * @return CompletionStage<Result>
	   */
	  
	  public CompletionStage<Result> getOwnerRepos(String searchKey) {
		  return ws.url(baseUrl + "/users/"+ searchKey+ "/repos")
			        .get()
			        .thenApplyAsync(result -> {	
			        	List<String> s1 = result.asJson().findValues("full_name").stream().map(JsonNode::asText).collect(Collectors.toList());
			        	List<String> s = new ArrayList<>();
 			        	for(String a: s1) {
			        		s.add(a.split("/")[1]);
			        	}
			        	
			        	return ok(views.html.owner.render(ownerMap.get(searchKey),s));
			        });
		   
	  }
	  
	  
	  
	  /**
	   * An action that returns the Repository's Profile Page.
	   * This will be called when a user clicks on a repository's name on the
	   * search results page or Owner Profile page.
	   * This method gets all the public information of a repositroy and binds it
	   * to an object of <code>PublicRepositoryInfo class<code>
	   * 
	   * @author Roxane
	   * @param searchKey owner's login
	   * @param SearchRepo owner's repository
	   * @return CompletionStage<Result>
	   */
	  
	  public CompletionStage<Result> getRepository(String searchKey, String SearchRepo) {
		  return ws.url(baseUrl + "/repos/"+ searchKey + "/" + SearchRepo)
			        .get()
			        .thenApplyAsync(result -> {	
			        	JsonNode jd =  result.asJson();
			        	JsonNode owner = jd.get("owner");
			        	PublicRepositoryInfo repo = Json.fromJson(jd, PublicRepositoryInfo.class);
			        	PublicOwnerInfo p = Json.fromJson(owner, PublicOwnerInfo.class);
			        	//return ok(result.asJson());
			        	return ok(views.html.repository.render(repo, p));
			        });
		  
	  }

	  
	
	        
}
