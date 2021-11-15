package dto;

import play.mvc.*;
import dto.formData;
import play.libs.ws.*;
import controllers.ApiController;

import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import org.junit.Test;
import play.data.FormFactory;
import play.data.FormFactory.*;
import play.i18n.MessagesApi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.node.*;

import org.junit.*;
import play.libs.Json;
import play.libs.ws.*;
import play.routing.RoutingDsl;
import play.server.Server;

import static play.mvc.Results.*;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsCollectionContaining.*;

import javax.inject.Inject;

public class ApiControllerTest {
	
	@Inject
	FormFactory formFactory;
	
	private WSClient ws;
	private play.data.Form<formData> form;
	private Http.Request request;
    private MessagesApi messagesApi;
   
    
    private ApiController ac = new ApiController(ws, formFactory, messagesApi);
    
    private Server server;

    @Before
    public void setup() {
      server =
          Server.forRouter(
              (components) ->
                  RoutingDsl.fromComponents(components)
                      .GET("/repositories?q=tetris")
                      .routingTo(
                          request -> {
                            ArrayNode repos = Json.newArray();
                            ObjectNode repo = Json.newObject();
                            repo.put("full_name", "chvin/react-tetris");
                            repos.add(repo);
                            return ok(repos);
                          })
                      .build());
      ws = play.test.WSTestClient.newClient(server.httpPort());
    }

    @After
    public void tearDown() throws IOException {
      try {
        ws.close();
      } finally {
        server.stop();
      }
    }

    @Test
    public void repositories() throws Exception {
    	
      Result repos = ac.searchQuery("tetris").toCompletableFuture().get(10, TimeUnit.SECONDS);
      assertEquals(OK, repos.status());
      
      //assertThat(repos.toString(), hasItem("chvin/react-tetris"));
    }
	
	/*@Test
	public void testIndex1() {
		ApiController ac = new ApiController(ws, formFactory, messagesApi);
		Result result = ac.index1();
		assertEquals(OK, result.status());
	}*/
}