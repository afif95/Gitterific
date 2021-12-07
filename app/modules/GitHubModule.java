package modules;
import com.google.inject.AbstractModule;

import javax.inject.Inject;

import models.GitHubApi; // Your API
import models.GitHubApiImpl; // Real implementation that you want
import models.GitHubApiMock;

public class GitHubModule extends AbstractModule {
	
	@Override
	protected final void configure() {
			//bind(GitHubApi.class).to(GitHubApiMock.class);
			bind(GitHubApi.class).to(GitHubApiImpl.class);
	}
}
