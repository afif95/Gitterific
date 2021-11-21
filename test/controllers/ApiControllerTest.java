package controllers;

import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.Request;

import play.mvc.Http.RequestBuilder;

import static play.test.Helpers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import play.test.Helpers;
import play.test.WithApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.Repository;


public class ApiControllerTest extends WithApplication{
	
	/**
	 * This test is used the check the status returned when
	 * the application when the application receives a
	 * <code>GET</code> request with a path of <code>/</code>
	 * and <code>index</code> method is invoked by <code>ApiController</code>.
	 */
	@Test
	public void testIndex() {
		
		RequestBuilder request = Helpers.fakeRequest(routes.ApiController.index());
		Result result = route(app, request);
		assertEquals(OK, result.status());
	}
	
	
	/**
	 * This test is used the check the status of the result returned when
	 * the application when the application receives a
	 * <code>GET</code> request with a path of <code>/home</code>
	 * and <code>showRepos</code> method is invoked by <code>ApiController</code>.
	 */
    @Test
    public void testShowRepos() {
        //add test when session id exist;
		RequestBuilder request = Helpers.fakeRequest(routes.ApiController.showRepos()).session("id", "1");
		Result result = route(app, request);
		Result result1 = route(app, request);

		assertEquals(Http.Status.OK, result.status());
		assertEquals(Http.Status.OK, result1.status());
		
		RequestBuilder request2 = Helpers.fakeRequest(routes.ApiController.showRepos());
		Result result2 = route(app, request2);
		assertEquals(Http.Status.OK, result2.status());
		
    }
    
    @Test
    public void testfetchRepos() {
       
    	Map<String, String> form2 = new HashMap<String, String>();
    	
        form2.put("searchInput", "java");
        RequestBuilder request = Helpers.fakeRequest(routes.ApiController.showRepos());
        request = Helpers.fakeRequest("POST","/home").bodyForm(form2).session("id","1");
		Result result = route(app, request);
		
		assertEquals(Http.Status.SEE_OTHER, result.status());
		
    }
    
    /**
	 * This test is used the check the status of the result 
	 * returned when the application when the application 
	 * receives a <code>GET</code> request for owner and his repositories 
	 * and <code>ApiController</code> invokes <code>getOwner</code> and 
	 * <code>getOwnerRepos</code> methods.
	 */
    @Test
    public void testGetOwner() {
    	String searchKey= "chvin";
		RequestBuilder request = Helpers.fakeRequest(routes.ApiController.getOwner(searchKey));
		Result result = route(app, request);
		assertEquals(Http.Status.SEE_OTHER, result.status());
		
		//test when key is already stored in ownermap
		RequestBuilder request2 = Helpers.fakeRequest(routes.ApiController.getOwner(searchKey));
		Result result2 = route(app, request2);
		assertEquals(Http.Status.SEE_OTHER, result2.status());
		
		//test getOwnerRepo
		RequestBuilder request3 = Helpers.fakeRequest(routes.ApiController.getOwnerRepos(searchKey));
		Result result3 = route(app, request3);
		assertEquals(Http.Status.OK, result3.status());
    }
    
    /**
	 * This test is used the check the status of the result 
	 * returned when the application when the application 
	 * receives a <code>GET</code> request for a repository
	 * and <code>ApiController</code> invokes <code>getRepository</code> and 
	 * <code>getOwnerRepos</code> methods.
	 */
    @Test
    public void testGetRepository() {
    	String searchKey= "chvin";
    	String searchRepo = "react-tetris";
		RequestBuilder request = Helpers.fakeRequest(routes.ApiController.getRepository(searchKey, searchRepo));
		Result result = route(app, request);
		assertEquals(Http.Status.OK, result.status());
    }
    
    /**
	 * This test is used the check the status of the result 
	 * returned by the application when the application 
	 * receives a <code>GET</code> request for a repository
	 * and <code>ApiController</code> invokes <code>getRepositoryIssues</code>
	 */
    @Test
    public void testGetRepositoryIssues() {
    	String searchKey= "chvin";
    	String searchRepo = "react-tetris";
		RequestBuilder request = Helpers.fakeRequest(routes.ApiController.getRepositoryIssues(searchKey, searchRepo));
		Result result = route(app, request);
		assertEquals(Http.Status.SEE_OTHER, result.status());
    }
    
    /**
	 * This test is used the check the status of the result 
	 * returned when the application when the application 
	 * receives a <code>GET</code> request for a repository 
	 * by a specific topic and <code>ApiController</code> 
	 * invokes <code>getReposByTpoic</code> and 
	 * <code>getOwnerRepos</code> methods.
	 */
    @Test
    public void  testGetReposByTopic() {
    	String searchKey= "topic";
    	RequestBuilder request = Helpers.fakeRequest(routes.ApiController.getReposByTopic(searchKey));
		Result result = route(app, request);
		assertEquals(Http.Status.OK, result.status());
    }
    
    /**
	 * This test is used the check the status of the result returned 
	 * when the application when the application receives a <code>GET</code> 
	 * request for issues and <code>ApiController</code> invokes <code>getIssues</code> 
	 * and <code>getIssues</code> methods.
	 */
    @Test
    public void testGetIssues() {
    	String owner = "chvin";
    	String reponame = "react-tetris";
		RequestBuilder request = Helpers.fakeRequest(routes.ApiController.getIssues(reponame, owner));

		Result result = route(app, request);
		assertEquals(Http.Status.OK, result.status());

    }
    
}