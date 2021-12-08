package actors;

import static play.inject.Bindings.bind;

import java.time.Duration;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import models.GitHubApi;
import models.GitHubApiMock;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.ws.WSClient;
import play.test.Helpers;

import static play.inject.Bindings.bind;

public class UserActorTest {

	private static ActorSystem system;

	private ActorRef timeActor;

	private static Application testApp;
	private static UserActor userActor;
	GitHubApi testApi;

	@Inject
	private WSClient wsc;

	/**
	 * Setup the actor system
	 */
	@BeforeClass
	public static void setup() {
		testApp = new GuiceApplicationBuilder().overrides(bind(GitHubApi.class).to(GitHubApiMock.class)).build();
	}

	@Before
	public void initAll() {

		testApi = testApp.injector().instanceOf(GitHubApi.class);
		system = ActorSystem.create();

		timeActor = system.actorOf(TimeActor.getProps(), "timeActor");
	}

	/*@Test
	public void testMainSearch() {
	
		final TestKit testProbe = new TestKit(system);
	
		final ActorRef userSearchActor = system
				.actorOf(UserActor.props(testProbe.getRef(), wsc, Helpers.fakeRequest().build().session()));
	
		final ObjectMapper mapper = new ObjectMapper();
	
		// testApp = new
		// GuiceApplicationBuilder().overrides(bind(GitHubApi.class).to(GitHubApiMock.class)).build();
	
		final ObjectNode request = mapper.createObjectNode();
	
		request.set("data", mapper.readValue("{\"message\" : \"java\"}", ObjectNode.class));
	
		userSearchActor.tell(request, ActorRef.noSender());
	
		testProbe.expectMsgClass(Duration.ofSeconds(10), ObjectNode.class);
	
	}*/

	/**
	 * Shutdown the actor system
	 */
	@AfterClass
	public static void teardown() {
		// TestKit.shutdownActorSystem(system);
		system = null;
	}

	/**
	 * Test for the UserActor
	 */
	/*
	 * @Test public void testUserActor() {
	 * 
	 * // TwitterService twitterService = testApp.instanceOf(TwitterService.class);
	 * // Materializer mat = ActorMaterializer.create(system);
	 * 
	 * final Props props = Props.create(UserActor.class); final
	 * TestActorRef<UserActor> subject = TestActorRef.create(system, props,
	 * "testC"); // userActor.setMat(mat);
	 * 
	 * userActor.
	 * 
	 * Map<String, ActorRef> searchResultsActorsMap = new HashMap<>();
	 * searchResultsActorsMap.put("concordia", subjectSra);
	 * userActor.setSearchResultsActors(searchResultsActorsMap);
	 * 
	 * subject.tell(new Messages.WatchSearchResults("concordia"), getRef()); // test
	 * registration // await the correct response
	 * expectMsgClass(duration("3 seconds"), Flow.class);
	 * 
	 * }
	 */

}
