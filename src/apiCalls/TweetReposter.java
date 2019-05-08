package apiCalls;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;

public class TweetReposter extends APICall {

	public TweetReposter(int numOfInfluencers, int numOfTweets) throws TwitterException, IOException, ParseException {
		// Sets number of tweets per influencer and grabs "numOfInfluencers" influencers
		Paging paging = new Paging(1, numOfTweets);
		ArrayList<String> targetedInfluencers = chooseRandomInfluencers(numOfInfluencers);
		
		for(String influencer : targetedInfluencers) {
			ResponseList<Status> results = twitter.getUserTimeline(influencer, paging);
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

	private static Status getBestPerformingTweet() {
		return null;
	}
}
