package apiCalls;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetPoster extends APICall{
	String[] dataPull = new String[] {"someecards", "shitgirlssay", "FirstWorldPains", "OhWonka"};
	
	public TweetPoster() throws TwitterException, IOException {
		super();
	}
	
	public Status repostBestTweet(String username, String password) throws TwitterException, IOException {
		ArrayList<String> usersToPullFrom = new ArrayList<String>(Arrays.asList(dataPull));
		return repostBestTweet(usersToPullFrom, username, password);
	}
	
	public Status repostBestTweet(ArrayList<String> usersToPullFrom, String username, String password) throws TwitterException, IOException {
		TweetGrabber grabber = new TweetGrabber();
		Status bestStatus = grabber.grab(usersToPullFrom);
		return postTweet(bestStatus, username, password);
	}
	
	private Status postTweet(Status tweet, String username, String password) throws TwitterException, FileNotFoundException {
		Twitter twitter = this.getAuthenticatedTwitterInstance(username, password);
		
		MediaEntity[] images = tweet.getMediaEntities();
		long[] mediaIDs = new long[images.length];
		
		for(int i = 0; i < images.length; i++) {
			mediaIDs[i] = images[i].getId();
		}
		
		StatusUpdate statusUpdate = new StatusUpdate(tweet.getText());
		statusUpdate.setMediaIds(mediaIDs);
		Status status = twitter.updateStatus(statusUpdate);
		return status;
	}

}
