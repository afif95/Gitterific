package controllers;

import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.Request;

import play.mvc.Http.RequestBuilder;

import static play.test.Helpers.*;
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
	
	
	@Test
	public void testIndex1() {
		
		RequestBuilder request = Helpers.fakeRequest(routes.ApiController.index1());
		Result result = route(app, request);
		assertEquals(OK, result.status());
	}
	
    @Test
    public void testfetchTweets() {
        //add test when session id exist;
		RequestBuilder request = Helpers.fakeRequest(routes.ApiController.showRepos());
		Result result = route(app, request);
		assertEquals(Http.Status.OK, result.status());
    	
    }
    
    //must create a request?
   /* @Test
    public void testFetchRepos() {
    	RequestBuilder request = Helpers.fakeRequest(routes.ApiController.fetchRepos());
		Result result = route(app, request);
		assertEquals(Http.Status.OK, result.status());
    }*/
    
  /*  @Test
    public void testConvertToRepo() throws JsonMappingException, JsonProcessingException {
    	String str = "{\"full_name\": \"fullNameOwner\",\"name\": \"nameOwner\", \"Owner\": \"owner\",\"topics\": [\"tetris\", \"game\"]}";
    	ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(str);
        
        Repository repo = controllers.ApiController.convertToRepo(json);
		assertEquals( Json.fromJson(json, Repository.class).getName(), repo.getName());
		assertEquals( Json.fromJson(json, Repository.class).getFull_name(), repo.getFull_name());
		assertEquals( Json.fromJson(json, Repository.class).getOwner(), repo.getOwner());
		assertEquals( Json.fromJson(json, Repository.class).getTopics(), repo.getTopics());

    }*/

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
    
    
    @Test
    public void testGetRepository() {
    	String searchKey= "chvin";
    	String searchRepo = "react-tetris";
		RequestBuilder request = Helpers.fakeRequest(routes.ApiController.getRepository(searchKey, searchRepo));
		Result result = route(app, request);
		assertEquals(Http.Status.OK, result.status());
    }
    
    @Test
    public void  testGetReposByTopic() {
    	String searchKey= "topic";
    	RequestBuilder request = Helpers.fakeRequest(routes.ApiController.getReposByTopic(searchKey));
		Result result = route(app, request);
		assertEquals(Http.Status.OK, result.status());
    }
    
    @Test
    public void testGetIssues() {
    	String url = "someUrl";
    	String owner = "chvin";
    	String reponame = "react-tetris";
		RequestBuilder request = Helpers.fakeRequest(routes.ApiController.getIssues(owner, reponame, url));
		Result result = route(app, request);
		assertEquals(Http.Status.OK, result.status());

    }
    
}