package apiCalls;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
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
	
	public Twitter getAuthenticatedTwitterInstance(String username, String password) {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		//the following is set without accesstoken- desktop client
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(consumerKey)
		.setOAuthConsumerSecret(consumerSecret);

		try {
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();

			try {
				System.out.println("-----");

				// get request token.
				// this will throw IllegalStateException if access token is already available
				// this is oob, desktop client version
				RequestToken requestToken = twitter.getOAuthRequestToken(); 

				System.out.println("Got request token.");
				System.out.println("Request token: " + requestToken.getToken());
				System.out.println("Request token secret: " + requestToken.getTokenSecret());

				System.out.println("|-----");

				AccessToken accessToken = null;

				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

				while (null == accessToken) {
					System.out.println("Open the following URL and grant access to your account:");
					System.out.println(requestToken.getAuthorizationURL());
					System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
					String pin = br.readLine();

					try {
						if (pin.length() > 0) {
							accessToken = twitter.getOAuthAccessToken(requestToken, pin);
						} else {
							accessToken = twitter.getOAuthAccessToken(requestToken);
						}
					} catch (TwitterException te) {
						if (401 == te.getStatusCode()) {
							System.out.println("Unable to get the access token.");
						} else {
							te.printStackTrace();
						}
					}
				}
				System.out.println("Got access token.");
				System.out.println("Access token: " + accessToken.getToken());
				System.out.println("Access token secret: " + accessToken.getTokenSecret());

			} catch (IllegalStateException ie) {
				// access token is already available, or consumer key/secret is not set.
				if (!twitter.getAuthorization().isEnabled()) {
					System.out.println("OAuth consumer key/secret is not set.");
				}
			}
			
			return twitter;
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			return null;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("Failed to read the system input.");
			return null;
		}
	}
}
