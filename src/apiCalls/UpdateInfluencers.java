package apiCalls;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.User;

public class UpdateInfluencers extends APICall {

	/**
	 * Updates Influencer.txt which stores information about the accounts that are being targeted for
	 * their content and followers. All of the accounts being followed by the twitter account associated
	 * with the api keys will be stored inside Influencer.txt along with the id's associated with these
	 * accounts.
	 * @throws TwitterException
	 * @throws IOException 
	 * @throws JSONException 
	 */
	@SuppressWarnings("unchecked")
	public UpdateInfluencers() throws TwitterException, IOException {
		IDs ids = twitter.getFriendsIDs(twitter.getId(), -1); // Gets following list
		ResponseList<User> users = twitter.lookupUsers(ids.getIDs()); // Generates a list of users from their ID's
		
//		//Displays the users screen name and id in console
//		for(int i = 0; i < users.size(); i++){
//			System.out.println(users.get(i).getScreenName() + ": " + users.get(i).getId());
//		}
		JSONObject json = new JSONObject();
		FileWriter file = new FileWriter("res/Influencers.json");
		if(json.containsKey(Long.toString(Long.MAX_VALUE)))
			json.remove(Long.toString(Long.MAX_VALUE));
		// Populates Influencers.txt with accounts under "Following"
		for(int i = 0; i < users.size(); i++) {
			json.put(users.get(i).getId(), users.get(i).getScreenName());
		}
		file.write(json.toString());
		file.close();
	}

}
