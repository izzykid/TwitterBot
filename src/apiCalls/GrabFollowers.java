package apiCalls;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.User;

public class GrabFollowers extends APICall {

	/**
	 * Grabs followers that will be targeted for following/ unfollowing. These targeted accounts
	 * are grabbed from the followers of accounts stored in Influencer.txt. Repeated users will not
	 * be stored into TargetedUsers.txt.
	 * @param grabAmount - Amount of accounts to be grabbed and stored for following/ unfollowing
	 * @throws TwitterException
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public GrabFollowers(String influencer, int grabAmount) throws TwitterException, IOException, ParseException {
		System.out.println("Grab " + influencer + "'s followers");
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("res/TargetedUsers.json"));
		JSONObject targetUsers = (JSONObject) obj;
		FileWriter file = new FileWriter("res/TargetedUsers.json");
		int indexOffset = 0;
		
		if(targetUsers.containsKey("username"))
			targetUsers.remove("username");
		// Get followers of the influencer
		try {
			IDs fids = twitter.getFollowersIDs(influencer, -1); // Get influencer's followers
			// Adds influencer to first index to instantiate the ResponseList
			ResponseList<User> users = twitter.lookupUsers(influencer); 
			for(int i = 0; i < grabAmount; i++) {
				//If the JSONObject already contains that user ID, skip adding that user again and increment grabAmount
				synchronized(this) {
					if(targetUsers.containsKey(twitter.lookupUsers(fids.getIDs()[i]).get(0).getScreenName())) {
						indexOffset++;
						grabAmount++;
						continue;
					}
				}
				users.add(twitter.lookupUsers(fids.getIDs()[i]).get(0));
				System.out.println(users.get(i + 1 - indexOffset).getScreenName());
				targetUsers.put(users.get(i + 1 - indexOffset).getScreenName(), "0");
			}
		}
		// If rate limit is reached, or there aren't enough unique IDs that were grabbed, don't throw an error.
		// Instead, just add the targeted users that were grabbed to TargetedUsers.json
		catch(IndexOutOfBoundsException | TwitterException e) { 
			
		}
		file.write(targetUsers.toString());
		file.close();
	}
}
