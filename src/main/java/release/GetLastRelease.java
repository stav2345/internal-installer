package release;

import java.io.IOException;

import http.HttpManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetLastRelease {
	
	private static final Logger LOGGER = LogManager.getLogger(GetLastRelease.class);
	
	private static final String URL_PATTERN = "https://api.github.com/repos/:username/:repo/releases/latest";
	
	private String endpoint;
	
	/**
	 * Get the last release of the repository
	 * @param githubUsername
	 * @param repositoryName
	 */
	public GetLastRelease(String githubUsername, String repositoryName) {
		
		this.endpoint = URL_PATTERN.replace(":username", githubUsername)
				.replace(":repo", repositoryName);
	}
	
	public String get() throws IOException {
		
		LOGGER.info("Sending Github request to " + endpoint);
		
		return HttpManager.get(endpoint);
	}
}
