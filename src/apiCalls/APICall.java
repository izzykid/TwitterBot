package apiCalls;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public abstract class APICall {

	protected static String consumerKey = "1W5c62ffTm7fOuOXj1frxiCiz";
	protected static String consumerSecret = "WFQumR6JvGGpx1AFahp8vQbdlzYgsHg8t6A0FDr4Wxsb2hZDlw";
	protected static String accessToken = "1115740636051677184-4a6UjEmDGO29wubIZThqCVmprDxua8";
	protected static String accessTokenSecret = "5iOlOnTwudHJTJxiuPFsAeRXQ4FefwkHgoC3PgShv98Rr";
	
	protected Twitter twitter;
	protected TwitterFactory twitterFactory;
	
	public APICall() throws TwitterException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
		.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret);
		twitterFactory = new TwitterFactory(cb.build());
		twitter = twitterFactory.getInstance();
	}
}
