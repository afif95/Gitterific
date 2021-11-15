package views;

import play.mvc.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.*;
import org.junit.Test;


class RouteTest {

	@Test
	void homeRouteTest() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Result result = routeAndCall(app, fakeRequest(GET, "/home"), 10);
				assertThat(status(result)).isEqualTo(OK);
			}
		});
	}

}
