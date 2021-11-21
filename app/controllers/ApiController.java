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
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import play.routing.RoutingDsl;
import play.server.Server;
import utils.UtilClass;
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
	private List<String> issues;// = new ArrayList<>();
	String baseUrl = "https://api.github.com";
	UtilClass util = new UtilClass();


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
	    this.issues = new ArrayList<>();
	  }
	  
	  /**
	     * An action that renders an HTML page with a welcome message.
	     * The configuration in the <code>routes</code> file means that
	     * this method will be called when the application receives a
	     * <code>GET</code> request with a path of <code>/</code>.
	     * 
	     * @author Amrit Singh/Rimsha Afzal
	     * @param request
	     */
	  
	  public Result index(Request request){
		     
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
	   * @author Amrit Singh/Rimsha Afzal
	   * @param request
	   * @return CompletionStage<Result>
	   */
	  
	  public CompletionStage<Result> showRepos(Request request) {
		  	String uid;
		  	 if((request.session().get("id")).isPresent()) {
		  		List<Repository> tmp = user_searches.get(request.session().get("id").get());
		  		//To prevent Nullpointer
		  		if(tmp == null) {
		  			tmp = new ArrayList<>();
		  		}
		  		 return CompletableFuture.completedFuture(ok(views.html.home.render(form, tmp, request, messagesApi.preferred(request))));
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
	   * The <code>json<code> from the api is converted into a java object using streams
	   * by methods implemented in <code>UtilClass class </code>.
	   * Finally, <code>CompletionStage<Result><code> is returned.
	   * 
	   * @author Amrit Singh/Rimsha Afzal
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
					return ws.url(baseUrl + "/search/repositories?q="+ searchVal  + "&per_page=10&sort=updated")
			        .get()			        
			        .thenApply(r -> {
			        	return util.JSONtoRepoList(r.asJson());
			        }).thenApply(list -> {
			        	String s = request.session().get("id").get();
			        	user_searches = util.addRepoToUserMap(s, user_searches, list);
			        	return user_searches.get(s);
			        }).thenApply(m -> redirect(routes.ApiController.showRepos()));
			        		
				} catch (Exception exp) {
					return CompletableFuture.completedFuture(ok());
				}
				
				

			}
		}
		
		
		 
		  /**
		   * An action that redirects to <code>getOwnerRepos<code> method.
		   * This will be called when a user clicks on the owner's name on the
		   * search results page.
		   * This method gets all the public information of an owner and binds it
		   * to an object of <code>PublicOwnerInfo class<code> by calling a
		   * method <code>insertKeyInOwnerMap </code> in <code>UtilClass class </code>.
		   * 
		   * @author Rimsha Afzal
		   * @param searchKey the login of the owner 
		   * @return CompletionStage<Result>
		   */	  
	  
		public CompletionStage<Result> getOwner(String searchKey) {
		  return ws.url(baseUrl + "/users/"+ searchKey)
			        .get()
			        .thenApplyAsync(result -> {	
			        	ownerMap = util.insertKeyInOwnerMap(result.asJson(), ownerMap, searchKey);
			        	return redirect(routes.ApiController.getOwnerRepos(searchKey));
			        });  
		}
		
		
		/**
		   * An action that returns the Owner's Profile Page.
		   * This will be called from <code>getOwner<code> method to fetch
		   * the name of the given owner's public repositories.
		   * 
		   * @author Rimsha Afzal
		   * @param searchKey the login of the owner 
		   * @return CompletionStage<Result>
		   */
		
	  
		public CompletionStage<Result> getOwnerRepos(String searchKey) {
		  return ws.url(baseUrl + "/users/"+ searchKey+ "/repos")
			        .get()
			        .thenApplyAsync(result -> {				        	
			        	return ok(views.html.owner.render(ownerMap.get(searchKey),util.getOwnerRepos(result.asJson())));
			        });   
		}
		
		/**
		 * A function to fetch the issues on a given repository.
		 * This will be called when a user clicks on a repository's name on the
		 * search results page or Owner Profile page.
		 * Then the function will redirect to the get Repository page.
		 * This method gets all the titles of the issues of that repository thanks to 
		 * the <code> getIssuesRepo </code> in <code>UtilClass class </code>.
		 * 
		 * @author Roxane Tissier
		 * @param searchKey
		 * @param SearchRepo
		 * @return CompletionStage<Result>
		 */
	  
	  
		public CompletionStage<Result> getRepositoryIssues(String searchKey, String SearchRepo){
		  return ws.url(baseUrl + "/repos/" + searchKey+"/"+SearchRepo+"/issues")
      			.get()
      			.thenApplyAsync(result -> {	
      				this.issues = util.getIssuesRepo(result.asJson());
      				return redirect(routes.ApiController.getRepository(searchKey, SearchRepo));
      			});
		}
	  
		/**
		   * An action that returns the Repository's Profile Page.
		   * This method gets all the public information of a repositroy and binds it
		   * to an object of <code>PublicRepositoryInfo class<code> by calling a
		   * method <code> getPublicRepositoryInfo </code> in <code>UtilClass class </code>.
		   * 
		   * @author Roxane Tissier
		   * @param searchKey owner's login
		   * @param SearchRepo owner's repository
		   * @return CompletionStage<Result>
		   */
	  
		public CompletionStage<Result> getRepository(String searchKey, String SearchRepo) {
		  return ws.url(baseUrl + "/repos/"+ searchKey + "/" + SearchRepo)
			        .get()
			        .thenApplyAsync(result -> {	
			        	return  ok(views.html.repository.render(util.getPublicRepositoryInfo(result.asJson()), 
			        						util.getPublicOwnerInfo(result.asJson()), this.issues));		
			        });
		}
	  
		/**
		   * An action that returns the Topics' Page.
		   * This will be called when a user clicks on a topic's name on the
		   * search results page.
		   * This method displays the 10 latest repositories that contain the
		   * selected topic  by calling a method <code> JSONtoRepoList </code> 
		   * in <code>UtilClass class </code>.
		   * 
		   * @author Amrit Singh
		   * @param searchKey the topic that was selected
		   * @return CompletionStage<Result>
		   */
		
		
		public CompletionStage<Result> getReposByTopic(String searchKey) {
		  return ws.url(baseUrl + "/search/repositories?q=topic:"+ searchKey + "&sort=updated&per_page=10")
			        .get()
			        
			        .thenApplyAsync(result -> {				        
			        	return ok(views.html.topics.render(util.JSONtoRepoList(result.asJson())));
			        });
		  
		}


 /**
  	 * This method is used to return frequency of all the words present in a specific repository.
	 * It takes repository and ownername as argument name
	 	
  * @author Afif Bin Kamrul
  * @param reponame The repository from which issues will be collected
  * @param owner The owner of the repository
  * @return CompletionStage<Result>

 */
public CompletionStage<Result> getIssues(String reponame, String owner){

		  return ws.url(baseUrl + "/repos/" + owner+"/"+reponame+"/issues").get()
				  .thenApplyAsync(result -> {	
					  
			        	
			        	return ok(views.html.issuestatistics.render(util.issue_to_title_frequency_descendingorder(util.issue_to_title_frequency(util.JsontoIssueList(result.asJson())))));

			        });

	        
	   }
	  

 
	        
}
