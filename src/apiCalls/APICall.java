package apiCalls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public abstract class APICall {

	protected static String consumerKey;// = "1W5c62ffTm7fOuOXj1frxiCiz";
	protected static String consumerSecret;// = "WFQumR6JvGGpx1AFahp8vQbdlzYgsHg8t6A0FDr4Wxsb2hZDlw";
	protected static String accessToken;// = "1115740636051677184-4a6UjEmDGO29wubIZThqCVmprDxua8";
	protected static String accessTokenSecret;// = "5iOlOnTwudHJTJxiuPFsAeRXQ4FefwkHgoC3PgShv98Rr";
	
	protected Twitter twitter;
	protected TwitterFactory twitterFactory;
	
	public APICall() throws TwitterException, IOException {
		// Grab API Keys from APIKeys.txt
		if(!new File("res/APIKeys.txt").exists()) {
			System.out.println("API keys need to be inputted into APIKeys.txt (found in res folder)");
		}
		else {
			Scanner sc = new Scanner(new File("res/APIKeys.txt"));
			consumerKey = sc.nextLine().split(":")[1];
			consumerSecret = sc.nextLine().split(":")[1];
			accessToken = sc.nextLine().split(":")[1];
			accessTokenSecret = sc.nextLine().split(":")[1];
			sc.close();
			
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
			.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret);
			
			twitterFactory = new TwitterFactory(cb.build());
			twitter = twitterFactory.getInstance();
		}
	}
	
	public Twitter getAuthenticatedTwitterInstance(String username, String password) throws FileNotFoundException {
		// Grab API Keys from APIKeys.txt
		if(!new File("res/APIKeys.txt").exists()) {
			System.out.println("API keys need to be inputted into APIKeys.txt (found in res folder)");
			return null;
		}
		else {
			Scanner sc = new Scanner(new File("res/APIKeys.txt"));
			consumerKey = sc.nextLine().split(":")[1];
			consumerSecret = sc.nextLine().split(":")[1];
			accessToken = sc.nextLine().split(":")[1];
			accessTokenSecret = sc.nextLine().split(":")[1];
			sc.close();

			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
			.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret).setUser(username).setPassword(password);

			TwitterFactory twitterFactory = new TwitterFactory(cb.build());
			Twitter twitter = twitterFactory.getInstance();
			return twitter;
		}
	}
}
