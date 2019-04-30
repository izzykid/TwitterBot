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
	public GrabFollowers(long influencerID, int grabAmount) throws TwitterException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("res/TargetedUsers.json"));
		JSONObject targetUsers = (JSONObject) obj;
		FileWriter file = new FileWriter("res/TargetedUsers.json");
		int indexOffset = 0;
		
		if(targetUsers.containsKey(Long.toString(Long.MAX_VALUE)))
			targetUsers.remove(Long.toString(Long.MAX_VALUE));
		// Get followers of the influencer
		IDs fids = twitter.getFollowersIDs(influencerID, -1); // Get influencer's followers
		System.out.println("Amount of ids: " + fids.getIDs().length);
		// Start at a random index of the IDs to maximize followers grabbed subtracted by the grabAmount to prevent
		// and IndexOutOfBoundsException
//		int startIndex = (int) Math.round(Math.random() * fids.getIDs().length) - grabAmount;
		// Adds influencer to first index to instantiate the ResponseList
		ResponseList<User> users = twitter.lookupUsers(influencerID); 
//		for(int i = startIndex; i < startIndex + grabAmount; i++) {
		for(int i = 0; i < grabAmount; i++) {
//			If the HashSet already contains that user ID, skip adding that user again and increment grabAmount
			synchronized(this) {
				if(targetUsers.containsKey(Long.toString(fids.getIDs()[i]))) {
					int tempOffset = indexOffset;
					indexOffset++;
					if(grabAmount != tempOffset + 1)
						grabAmount = tempOffset + 1;
					System.out.println("Before: " + tempOffset + " After: " + indexOffset);
					System.out.println("Grab before: " + grabAmount);
					grabAmount++;
					System.out.println("Grab after: " + grabAmount);
					continue;
				}
			}
//			if(targetUsers.containsKey(Long.toString(fids.getIDs()[i]))) {
//				int tempGrabAmount = grabAmount;
//				grabAmount++;
//				if(grabAmount != tempGrabAmount + 1)
//					grabAmount = tempGrabAmount + 1;
//				System.out.println(grabAmount);
//				continue;
//			}
			users.add(twitter.lookupUsers(fids.getIDs()[i]).get(0));
			System.out.println(fids.getIDs()[i] + ": " + users.get(i - indexOffset/* - startIndex*/).getScreenName());
			targetUsers.put(Long.toString(fids.getIDs()[i]), users.get(i - indexOffset/* - startIndex*/).getScreenName());
		}
		file.write(targetUsers.toString());
		file.close();
//		// Populates TargetedUsers.txt with accounts that are following accounts from Influencers.txt
//		BufferedWriter writer = new BufferedWriter(new FileWriter("res/TargetedUsers.txt", true));
//		for(int i = 1; i < users.size(); i++) {
//			writer.append(users.get(i).getScreenName() + "," + users.get(i).getId() + "," + "0" + "\n");
//		}
//		writer.close();
	}
}
