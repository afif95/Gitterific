package views;
import pages.HomePage;

import static org.junit.Assert.assertEquals;

import static play.test.Helpers.*;
import org.junit.Test;

class ViewTests {

	@Test
	void testHome() {
		running(testServer(9002, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
			public void invoke(TestBrowser browser) {
				HomePage home = new HomePage(browser.getDriver(), 9002);
				browser.goTo(home);
				assertEquals("http://localhost:9002/home", home.getUrl());
			}
		});
		
	}

}
