package actors;


import akka.testkit.javadsl.TestKit;
import models.GitHubApi;
import models.GitHubApiMock;
import akka.actor.testkit.typed.javadsl.ActorTestKit;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.test.WithApplication;
import utils.utilClass;
import play.mvc.Http;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import actors.TimeActor.RegisterMsg;
import actors.TimeActor.Tick;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.AbstractActor;

import static play.inject.Bindings.bind;

import java.time.Duration;

public class TestKitSampleTest extends WithApplication{
	 static final ActorTestKit testKit = ActorTestKit.create();
	 private static Application testApp;
	 private static GitHubApi testGitHub;
	 
	 static ActorSystem system;
	 ActorRef timeActor;

	 Http.Session session;
	 private WSClient wsc;
	 
	utilClass util = new utilClass();

	 String jsonStr = "{\"items\" : [{\"owner\" : {\"login\" : \"User\", \"id\" : 1}, \"full_name\" : \"login\", \"topics\" : [\"topic\"]}]}";
	 
	 @BeforeClass
		public static void initTestApp() {
			testApp = new GuiceApplicationBuilder()
							.overrides(bind(GitHubApi.class).to(GitHubApiMock.class))
							.build();
			
			testGitHub = testApp.injector().instanceOf(GitHubApi.class);
			system = ActorSystem.create();
		}
	 
	 @AfterClass
	 public static void cleanup() {
	   testKit.shutdownTestKit();
	 }
  
	 
	/* @Test
	  public void testSomething() {
	   
		TestKit test = new TestKit(system);
		//ActorRef timeActor = system.actorOf(TimeActor.getProps());
		ActorRef userActor = system.actorOf(UserActor.props(timeActor, wsc, session));
		
		
		ObjectNode response =  Json.newObject();
		response.putPOJO("data", util.createJson(jsonStr));
		userActor.tell(response, ActorRef.noSender());
		//userActor.tell(test.getRef(), ActorRef.noSender());
	    //TestProbe<Echo.Pong> probe = testKit.createTestProbe();
	    //pinger.tell(new Echo.Ping("hello", probe.ref()));
	    test.expectMsgClass(ObjectNode.class);
	    
	  }*/
	 
}