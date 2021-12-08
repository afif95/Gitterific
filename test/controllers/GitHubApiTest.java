package controllers;
import org.junit.*;

import com.fasterxml.jackson.databind.JsonNode;

import dto.Repository;
import models.GitHubApi;
import models.GitHubApiImpl;
import models.GitHubApiMock;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;

import static org.junit.Assert.assertEquals;
import static play.inject.Bindings.bind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import play.test.Helpers;
import play.test.WithApplication;
import utils.Singleton;
import utils.utilClass;


public class GitHubApiTest extends WithApplication{
	private static Application testApp;
	private static GitHubApi testGitHub;
	static utilClass util = new utilClass();

	String jsonForFetch = "[{\"owner\":{\"login\":\"User\",\"id\":1},\"full_name\":\"login\",\"topics\":[\"topic\"]}]";
	String jsonRepo = "[{\"login\" : \"User\" , \"id\" : 1, \"node_id\" : \"NJueibhcOIJB875DCBNdcsj\"}]";

	static Map<String, List<Repository>> userSearches = new HashMap<>();

	
	@BeforeClass
	public static void initTestApp() {
		testApp = new GuiceApplicationBuilder()
						.overrides(bind(GitHubApi.class).to(GitHubApiMock.class))
						.build();
		
		testGitHub = testApp.injector().instanceOf(GitHubApi.class);

	}
	
	@AfterClass
	public static void stopTestApp() {
		Helpers.stop(testApp);
	}
	
	@Test
	public void fetchResultImpTest() throws Exception{
		CompletionStage<JsonNode> result = testGitHub.fetchResultsImp(userSearches, "string", null, null, null, null, null);
        CompletableFuture<JsonNode> future = result.toCompletableFuture();
        JsonNode resul= future.get();
		assertEquals(jsonForFetch, resul.get("data").toString());
	}
	
	@Test 
	public void fetchRepositoryImpTest() throws Exception{
	/*	CompletionStage<JsonNode> result = testGitHub.fetchRepositoryImp(userSearches, "string", null, null, null, null, null);
        CompletableFuture<JsonNode> future = result.toCompletableFuture();

        JsonNode resul= future.get();
		assertEquals(jsonRepo, resul.get("data").toString());*/
	}
	
	@Test 
	public void fetchOwnerImpTest() throws Exception{
		CompletionStage<JsonNode> result = testGitHub.fetchOwnerImp(userSearches, "string", null, null, null, null, null);
        CompletableFuture<JsonNode> future = result.toCompletableFuture();
        JsonNode resul= future.get();
		assertEquals(jsonForFetch, resul.get("data").toString());
	}
	
	
	
}