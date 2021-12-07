package models;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import actors.UserActor;
import akka.actor.ActorRef;
import dto.Repository;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http.Session;
import utils.Singleton;

import javax.inject.Inject;


public interface GitHubApi {
	CompletionStage<JsonNode> fetchResultsImp(Map<String, List<Repository>> userSearches, String searchVal, ActorRef ws, WSClient wsc, ActorRef ua, Session session, Singleton singleton);
	CompletionStage<JsonNode> fetchOwnerImp(Map<String, List<Repository>> userSearches, String searchVal, ActorRef ws, WSClient wsc, ActorRef ua, Session session, Singleton singleton);
	CompletionStage<JsonNode> fetchRepositoryImp(Map<String, List<Repository>> userSearches, String searchVal, ActorRef ws, WSClient wsc, ActorRef ua, Session session, Singleton singleton);

	//CompletionStage<WSResponse> fetchingUpdateImp(Map<String, List<Repository>> userSearches, ActorRef ws, WSClient wsc, Session session, Singleton singleton);
	
/*	ObjectNode fetchResultsImp(JsonNode r,  Map<String, List<Repository>> userSearches, String searchVal);
	ObjectNode fetchingUpdateImp(JsonNode r,  Map<String, List<Repository>> userSearches, String searchVal);*/
}
