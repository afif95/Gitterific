package views;
import play.test.WithApplication;
import play.twirl.api.Content;
import views.html.*;
import static org.junit.Assert.assertEquals;

import static play.test.Helpers.*;
import org.junit.Test;

import controllers.routes;

class ViewTests extends WithApplication{

	/*@Test
	void testHome() {
		running(testServer(9002, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
			public void invoke(TestBrowser browser) {
				HomePage home = new HomePage(browser.getDriver(), 9002);
				browser.goTo(home);
				assertEquals("http://localhost:9002/home", home.getUrl());
			}
		});
		
	}
	
	@Test
	public void renderTemplate() {
	   Content html = views.html.;//views.html.home.render();
	  assertEquals("text/html", html.contentType());
	  //assertTrue(contentAsString(html).contains("Welcome to Play!"));
	}*/

}
