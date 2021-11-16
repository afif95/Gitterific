package views;

import play.mvc.*;
import play.mvc.Http.RequestBuilder;
import play.test.Helpers;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;
import org.junit.Test;

import controllers.routes;


class RouteTest extends WithApplication{

	
	
	@Test
	void homeRouteTest() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Result result = routeAndCall(app, fakeRequest(GET, "/home"), 10);
				assertEquals(Http.Status.OK, result.status());
			}
		});
	}
	
	@Test
	void ownerRouteTest() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Result result = routeAndCall(app, Helpers.fakeRequest(GET, "/owner/chvin"), 10);
				assertEquals(Http.Status.OK, result.status());
			}
		});
	}

}
