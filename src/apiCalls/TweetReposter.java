package apiCalls;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import core.Launcher;
import twitter4j.MediaEntity;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

public class TweetReposter extends APICall {

	/**
	 * 
	 * @param numOfInfluencers - number of influencers the user wants to search for tweets from
	 * @param numOfTweets
	 * @throws TwitterException
	 * @throws IOException
	 * @throws ParseException
	 */
	public TweetReposter(int numOfInfluencers, int numOfTweets) throws TwitterException, IOException, ParseException {
		// Sets number of tweets per influencer and grabs "numOfInfluencers" influencers
		ArrayList<String> targetedInfluencers = chooseRandomInfluencers(numOfInfluencers);
		ArrayList<Status> bestTweets = grab(targetedInfluencers, numOfTweets);
		if(bestTweets.size() < 1) {
			throw new IOException("No tweets grabbed!!");
		}
		boolean posted = false;
		Status chosenTweet = bestTweets.get(bestTweets.size() - 1);
		posted = postTweet(chosenTweet);
		if(!posted) {
			for(int i = bestTweets.size() - 2; i >= 0; i--) {
				chosenTweet = bestTweets.get(i);
				posted = postTweet(chosenTweet);
				if(posted) {
					break;
				}
			}
			if(!posted) {
				throw new IOException("Post failed");
			}
		}
		else {
			Launcher.lblWarning.setText("Posted");
		}
	}
	/**
	 * Grabs a list of influencers at random, based on how many influencers the user wants
	 * to be selected
	 * @param numOfInfluencers - the amount of influencers that tweets will be grabbed from
	 * @return targetedInfluencers - the randomly selected influencers
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static ArrayList<String> chooseRandomInfluencers(int numOfInfluencers) throws FileNotFoundException, IOException, ParseException {
		// Initialize variables needed to grab "numOfInfluencers" influencers
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("res/Influencers.json"));
		JSONObject targetUsers = (JSONObject) obj;
		Iterator<?> it = targetUsers.entrySet().iterator();
		ArrayList<String> allInfluencers = new ArrayList<String>();
		ArrayList<String> targetedInfluencers = new ArrayList<String>();
		
		// Add all influencers to an ArrayList
		while(it.hasNext()) {
			@SuppressWarnings("unchecked")
			Entry<String, String> pair = (Entry<String, String>) it.next();
			allInfluencers.add(pair.getKey());
		}
		
		// If the amount of desired influencers is more than what is stored, return null
		if(numOfInfluencers > allInfluencers.size())
			return null;
		
		// Randomly add numOfInfluencers to targetedInfluencers
		for(int i = 0; i < numOfInfluencers; i++) {
			int randIndex = (int) Math.round(Math.random() * allInfluencers.size());
			targetedInfluencers.add(allInfluencers.get(randIndex));
			allInfluencers.remove(randIndex);
		}
		return targetedInfluencers;
	}
	/**
	 * Pulls the most recent 100 tweets from each person's timeline and grades them all against eachother for most engagement.
	 * @param usersToPullFrom
	 * @return The tweet with the most engagement
	 * @throws TwitterException
	 * @throws IOException
	 */
	public ArrayList<Status> grab(List<String> usersToPullFrom, int numOfTweets) throws TwitterException, IOException {
		if(usersToPullFrom.size() < 1) {
			throw new IOException();
		}
		ArrayList<Status> tweets = new ArrayList<Status>();
		int maxGrade = 0;
		Status bestTweet = null;
		//Iterates though each user and gets the 100 most recent posts from each user
		Paging paging = new Paging(1, numOfTweets);
		for(String user: usersToPullFrom) {
			ResponseList<Status> results = twitter.getUserTimeline(user, paging);
			//Grades the results
			for(Status tweet: results) {
				int grade = gradeTweet(tweet);
				//Stores the better result between the current best and current tweet
				if(grade >= maxGrade) {
					tweets.add(tweet);
					maxGrade = grade;
					bestTweet = tweet;
				}
			}
		}
		return tweets;
	}
	/**
	 * @param tweet
	 * @return A grade based on the engagement of that tweet
	 */
	private int gradeTweet(Status tweet) {
		if(tweet.getMediaEntities().length < 1) {
			return 0;
		}
		
		int grade = tweet.getFavoriteCount();
		grade += tweet.getRetweetCount() * 2;
		
		//Adding an element of oldness to help prevent us being caught for reposting
		//Each day since today adds a point to the grade
		Date date = new Date();
		grade += (int)((date.getTime() - tweet.getCreatedAt().getTime()) / (long)(86400000));
		
		return grade;
	}
	/**
	 * Returns whether or not twitterbot was able to post the tweet
	 * @param tweet
	 * @return
	 * @throws TwitterException
	 * @throws FileNotFoundException
	 */
	private boolean postTweet(Status tweet){
		MediaEntity[] images = tweet.getMediaEntities();
		
		String post = tweet.getText();
		for(int i = 0; i < images.length; i++) {
			post += images[i].getMediaURL() + " ";
		}
		
		StatusUpdate statusUpdate = new StatusUpdate(tweet.getText());
		//statusUpdate.setMediaIds(mediaIDs);
		try {
			Status status = twitter.updateStatus(statusUpdate);
			return true;
		} catch (TwitterException e) {
			return false;
		}
	}
}
