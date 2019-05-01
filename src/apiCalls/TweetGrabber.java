package apiCalls;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
/**
 * 
 * @author jason
 *
 */
public class TweetGrabber extends APICall{

	public TweetGrabber() throws TwitterException, IOException {
		super();
	}
	/**
	 * Pulls the most recent 100 tweets from each person's timeline and grades them all against eachother for most engagement.
	 * @param usersToPullFrom
	 * @return The tweet with the most engagement
	 * @throws TwitterException
	 * @throws IOException
	 */
	public Status grab(List<String> usersToPullFrom) throws TwitterException, IOException {
		if(usersToPullFrom.size() < 1) {
			throw new IOException();
		}
		
		int maxGrade = 0;
		Status bestTweet = null;
		//Iterates though each user and gets the 100 most recent posts from each user
		Paging paging = new Paging(1, 100);
		for(String user: usersToPullFrom) {
			ResponseList<Status> results = twitter.getUserTimeline(user, paging);
			//Grades the results
			for(Status tweet: results) {
				int grade = gradeTweet(tweet);
				//Stores the better result between the current best and current tweet
				if(grade >= maxGrade) {
					maxGrade = grade;
					bestTweet = tweet;
				}
			}
		}
		
		return bestTweet;
	}
	
	/**
	 * @param tweet
	 * @return A grade based on the engagement of that tweet
	 */
	private int gradeTweet(Status tweet) {
		int grade = tweet.getFavoriteCount();
		grade += tweet.getRetweetCount() * 2;
		
		//Adding an element of oldness to help prevent us being caught for reposting
		//Each day since today adds a point to the grade
		Date date = new Date();
		grade += (int)((date.getTime() - tweet.getCreatedAt().getTime()) / (long)(86400000));
		
		return grade;
	}

}
