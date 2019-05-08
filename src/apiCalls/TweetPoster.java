package apiCalls;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TweetPoster extends APICall {
	
	public RequestToken requestToken = null;
	public AccessToken accessToken = null;
	
	public TweetPoster() throws TwitterException, IOException, ParseException {
		super();
//		repostBestTweet();
	}
	
	public Status repostBestTweet() throws TwitterException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("res/Influencers.json"));
		JSONObject targetUsers = (JSONObject) obj;
		Iterator it = targetUsers.entrySet().iterator();
		ArrayList<String> usersToPullFrom = new ArrayList<String>();
		while(it.hasNext()) {
			System.out.println(it.next());
//			usersToPullFrom.add();
		}
//		ArrayList<String> usersToPullFrom = new ArrayList<String>(Arrays.asList(dataPull));
		return repostBestTweet(usersToPullFrom);
	}
	
	public Status repostBestTweet(ArrayList<String> usersToPullFrom) throws TwitterException, IOException, ParseException {
		TweetGrabber grabber = new TweetGrabber();
		Status bestStatus = grabber.grab(usersToPullFrom);
		return postTweet(bestStatus);
	}
	
	private Status postTweet(Status tweet) throws TwitterException, FileNotFoundException {
		MediaEntity[] images = tweet.getMediaEntities();
		
		String post = tweet.getText();
		for(int i = 0; i < images.length; i++) {
			post += images[i].getMediaURL() + " ";
		}
		StatusUpdate statusUpdate = new StatusUpdate(tweet.getText());
		//statusUpdate.setMediaIds(mediaIDs);
		Status status = twitter.updateStatus(statusUpdate);
		return status;
	}

//	public RequestToken getRequestToken() throws FileNotFoundException, TwitterException {
////		// Grab API Keys from APIKeys.txt
//		if(!new File("res/APIKeys.txt").exists()) {
//			System.out.println("API keys need to be inputted into APIKeys.txt (found in res folder)");
//			return null;
//		}
//		else {
//			Scanner sc = new Scanner(new File("res/APIKeys.txt"));
//			consumerKey = sc.nextLine().split(":")[1];
//			consumerSecret = sc.nextLine().split(":")[1];
//			sc.close();
//
//			ConfigurationBuilder cb = new ConfigurationBuilder();
//			cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret);
//
//			twitterFactory = new TwitterFactory(cb.build());
//			twitter = twitterFactory.getInstance();
//			
//			requestToken = twitter.getOAuthRequestToken();
//			return requestToken;
////		}
//		return twitter.getOAuthRequestToken();
//	}
	
	public Twitter getAuthenticatedSession(String pin) throws TwitterException, FileNotFoundException {
		accessToken = twitter.getOAuthAccessToken(requestToken, pin);
//		Scanner sc = new Scanner(new File("res/APIKeys.txt"));
//		consumerKey = sc.nextLine().split(":")[1];
//		consumerSecret = sc.nextLine().split(":")[1];
//		sc.close();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
		.setOAuthAccessToken(accessToken.getToken()).setOAuthAccessTokenSecret(accessToken.getTokenSecret());
//		twitterFactory = new TwitterFactory(cb.build());
//		twitter = twitterFactory.getInstance();
		return twitter;
	}
}
