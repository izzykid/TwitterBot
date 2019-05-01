package apiCalls;

import java.io.IOException;
import java.util.ArrayList;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

public class TweetPoster extends APICall{

	public TweetPoster() throws TwitterException, IOException {
		super();
	}
	
	public void repostBestTweet(ArrayList<String> usersToPullFrom) throws TwitterException, IOException {
		TweetGrabber grabber = new TweetGrabber();
		Status bestStatus = grabber.grab(usersToPullFrom);
		postTweet(bestStatus);
	}
	
	private void postTweet(Status tweet) throws TwitterException {
		MediaEntity[] images = tweet.getMediaEntities();
		long[] mediaIDs = new long[images.length];
		
		for(int i = 0; i < images.length; i++) {
			mediaIDs[i] = images[i].getId();
		}
		
		StatusUpdate statusUpdate = new StatusUpdate(tweet.getText());
		statusUpdate.setMediaIds(mediaIDs);
		Status status = twitter.updateStatus(statusUpdate);
	}

}
