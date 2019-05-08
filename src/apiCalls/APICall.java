package apiCalls;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import core.TwitterApp;
import interact.GrabAccessPIN;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import utils.Utils;

public abstract class APICall {
	
	protected static Twitter twitter;
	protected static TwitterFactory twitterFactory;
	
	public APICall() throws TwitterException, IOException, ParseException {
		// Grab API Keys from APIKeys.txt
		if(!new File("res/APIKeys.json").exists()) {
			System.out.println("res/APIKeys.json was deleted, please restart the application");
		}
		else {
			// If the account has not been authorized yet, grab the pin and generate access tokens
			if(!Utils.isAuthorized()) 
				new GrabAccessPIN();
			
			// Grab keys associated with APIKeys.json
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader("res/APIKeys.json"));
			JSONObject keys = (JSONObject) obj;
			String tokens = (String) keys.get(Utils.getLogInInfo()[0]);
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true).setOAuthConsumerKey("1W5c62ffTm7fOuOXj1frxiCiz")
			.setOAuthConsumerSecret("WFQumR6JvGGpx1AFahp8vQbdlzYgsHg8t6A0FDr4Wxsb2hZDlw")
			.setOAuthAccessToken(tokens.split(";")[0].trim()).setOAuthAccessTokenSecret(tokens.split(";")[1].trim());

			twitterFactory = new TwitterFactory(cb.build());
			twitter = twitterFactory.getInstance();
			
			TwitterApp.setTwitter(twitter);
			TwitterApp.setTwitterFactory(twitterFactory);
			
		}
	}
}
