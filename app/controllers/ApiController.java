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
import java.util.HashMap;
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
	private List<String> issues;
	String baseUrl = "https://api.github.com";
	UtilClass util = new UtilClass();



    
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
	  
	/*  public Result home(Request request) {
		  List<Repository> repoList = Repository.getRepos();
	        return ok(views.html.home.render(formFactory.form(formData.class), repoList, messagesApi.preferred(request)));
	   }*/
	  
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
		    	//Optional<List<Repository>> opt_list = Optional.ofNullable(repo); 
		    	user_searches.put(uid, repo);
		        return CompletableFuture.completedFuture(ok(views.html.home.render(form, repo, request, messagesApi.preferred(request))).addingToSession(request, "id",uid));
		    	//return CompletableFuture.completedFuture(ok(Json.toJson(uid)).addingToSession(request, "id",uid));
		  	 }
		}
	  
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
	  
	  public CompletionStage<Result> getOwner(String searchKey) {
		  return ws.url(baseUrl + "/users/"+ searchKey)
			        .get()
			        .thenApplyAsync(result -> {	
			        	ownerMap = util.insertKeyInOwnerMap(result.asJson(), ownerMap, searchKey);
			        	return redirect(routes.ApiController.getOwnerRepos(searchKey));
			        });
		  
	  }
	  
	  public CompletionStage<Result> getOwnerRepos(String searchKey) {
		  return ws.url(baseUrl + "/users/"+ searchKey+ "/repos")
			        .get()
			        .thenApplyAsync(result -> {				        	
			        	return ok(views.html.owner.render(ownerMap.get(searchKey),util.getOwnerRepos(result.asJson())));
			        });
		   
	  }
	  
	  
	 public CompletionStage<Result> getRepositoryIssues(String searchKey, String SearchRepo){
		  return ws.url(baseUrl + "/repos/" + searchKey+"/"+SearchRepo+"/issues")
      			.get()
      			.thenApplyAsync(result -> {	
      				issues = util.getIssuesRepo(result.asJson());
      				return redirect(routes.ApiController.getRepository(searchKey, SearchRepo));
      			});
	  }
	  
	  public CompletionStage<Result> getRepository(String searchKey, String SearchRepo) {
		  return ws.url(baseUrl + "/repos/"+ searchKey + "/" + SearchRepo)
			        .get()
			        .thenApplyAsync(result -> {	
			        	
			        	return  ok(views.html.repository.render(util.getPublicRepositoryInfo(result.asJson()), 
			        						util.getPublicOwnerInfo(result.asJson()), issues));
			        
			        			
			        			
			        });
		  
	  }
	  
	  
	  
	  public CompletionStage<Result> getReposByTopic(String searchKey) {
		  return ws.url(baseUrl + "/search/repositories?q=topic:"+ searchKey + "&sort=updated&per_page=10")
			        .get()
			        
			        .thenApplyAsync(result -> {				        
			        	return ok(views.html.topics.render(util.JSONtoRepoList(result.asJson())));
			        });
		  
	  }


 public CompletionStage<Result> getIssues(String reponame, String owner, String issueurl){
		  
		  //String trimmed =    issueurl.substring(0, issueurl.length()-9);
		  
		//  return ws.url(baseUrl + "/search/issues?q=repo" + owner+"/"+reponame).get()
		  
		  return ws.url(baseUrl + "/repos/" + owner+"/"+reponame+"/issues").get()
				  .thenApplyAsync(result -> {	
					  
			        	//List<String> s = result.asJson().findValues("title").stream().map(JsonNode::asText).collect(Collectors.toList());
			        	//Map<String, Integer> freq = s.parallelStream().flatMap(sob -> Arrays.asList(sob.split(" ")).stream()).collect(Collectors.toConcurrentMap(sob1->sob1, sob1 ->1, Integer::sum));
			        	//return ok(views.html.issuestatistics.render(freq));
			        	return ok(views.html.issuestatistics.render(util.getIssues(result.asJson().findValues("title").stream().map(JsonNode::asText).collect(Collectors.toList()))));
			        });

	        //return ok(views.html.issuestatistics.render(trimmed,reponame, owner));
	        
	   }
	  
 /*public CompletionStage<Void> frequency(String issuesingle){
      issueresult = Arrays.stream(issuesingle.split(" "))
    		  .map(String::trim)
    		  .toArray(String[]::new);
	 
 }*/
 
 
	/*   public CompletionStage<Repository> getRepos(final String keywords) {
           return ws.url(baseUrl + "/search/repositories?q="+ keywords + "&per_page=5")
       	        .get()
                   .thenApplyAsync(WSResponse::asJson)
                   .thenApplyAsync(this::convertToRepo);
   } */
	 
	        
}
