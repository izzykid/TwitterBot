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
	
	public Status repostBestTweet() throws TwitterException, IOException {
		ArrayList<String> usersToPullFrom = new ArrayList<String>(Arrays.asList(dataPull));
		return repostBestTweet(usersToPullFrom);
	}
	
	public Status repostBestTweet(ArrayList<String> usersToPullFrom) throws TwitterException, IOException {
		TweetGrabber grabber = new TweetGrabber();
		Status bestStatus = grabber.grab(usersToPullFrom);
		return postTweet(bestStatus);
	}
	
	private Status postTweet(Status tweet) throws TwitterException, FileNotFoundException {
		Twitter twitter = this.getAuthenticatedTwitterInstance();
		
		MediaEntity[] images = tweet.getMediaEntities();
		long[] mediaIDs = new long[images.length];
		
		for(int i = 0; i < images.length; i++) {
			mediaIDs[i] = images[i].getId();
		}
		
		StatusUpdate statusUpdate = new StatusUpdate(tweet.getText() + " ");
		statusUpdate.setMediaIds(mediaIDs);
		Status status = twitter.updateStatus(statusUpdate);
		return status;
	}

}
