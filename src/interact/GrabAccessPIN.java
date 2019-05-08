package interact;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;

import apiCalls.APICall;
import core.TwitterApp;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import utils.Utils;

public class GrabAccessPIN extends SeleniumCall {

	public GrabAccessPIN() throws TwitterException, IOException, ParseException {
		if(!new File("res/APIKeys.txt").exists()) {
			System.out.println("API keys need to be inputted into APIKeys.txt (found in res folder)");
		}
		else {
			Scanner sc = new Scanner(new File("res/APIKeys.txt"));
			String consumerKey = sc.nextLine().split(":")[1];
			String consumerSecret = sc.nextLine().split(":")[1];
			sc.close();
			
			// Create new ConfigurationBuilder for a new user
			ConfigurationBuilder cbTemp = new ConfigurationBuilder();
			cbTemp.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret);
			
			// Initialize temporary TwitterFactory/ Twitter
			TwitterFactory twitterFactory = new TwitterFactory(cbTemp.build());
			Twitter twitter = twitterFactory.getInstance();
			
			// Selenium grabs accessPIN
			RequestToken requestToken = twitter.getOAuthRequestToken();
			driver.get(requestToken.getAuthorizationURL());
			Utils.waitUntilVisible("//*[@id=\"username_or_email\"]", driver, 15);
			driver.findElement(By.xpath("//*[@id=\"username_or_email\"]")).sendKeys(Utils.getLogInInfo()[0]);
			Utils.waitUntilVisible("//*[@id=\"password\"]", driver, 15);
			driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(Utils.getLogInInfo()[1]);
			Utils.waitUntilClickable("//*[@id=\"allow\"]", driver, 15);
			driver.findElement(By.xpath("//*[@id=\"allow\"]")).click();
			Utils.waitUntilVisible("//*[@id=\"oauth_pin\"]", driver, 15);
			String accessPIN = driver.findElement(By.xpath("//*[@id=\"oauth_pin\"]")).getText().split(":")[1].trim();
		
			// Grab accessToken from accessPIN
			AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, accessPIN);
			
			// Create new Twitter object with the new accessTokens
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true).setOAuthAccessToken(accessToken.getToken()).setOAuthAccessTokenSecret(accessToken.getTokenSecret());
			twitterFactory = new TwitterFactory(cb.build());
			twitter = twitterFactory.getInstance();
			
			TwitterApp.setTwitter(twitter);
			TwitterApp.setTwitterFactory(twitterFactory);
			
			Utils.setAuthorizedTrue();
			addToJson(accessToken);
			
			driver.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void addToJson(AccessToken accessToken) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("res/APIKeys.json"));
		JSONObject keys = (JSONObject) obj;
		FileWriter file = new FileWriter("res/APIKeys.json");
		
		keys.put(accessToken.getScreenName(), accessToken.getToken() + ";" + accessToken.getTokenSecret());
		file.write(keys.toString());
		file.close();
	}

}