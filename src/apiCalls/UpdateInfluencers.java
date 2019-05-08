package apiCalls;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

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
	public UpdateInfluencers(String username) throws TwitterException, IOException, ParseException {
		System.out.println("Update Influencers");
		IDs ids = twitter.getFriendsIDs(username, -1); // Gets following list
		ResponseList<User> users = twitter.lookupUsers(ids.getIDs()); // Generates a list of users from their ID's
		// Creates a JSONObject to be used for storing users and their IDs
		JSONObject json = new JSONObject();
		FileWriter file = new FileWriter("res/Influencers.json");
		if(json.containsKey("username"))
			json.remove("username");
		// Populates Influencers.txt with accounts under "Following"
		for(int i = 0; i < users.size(); i++) {
			json.put(users.get(i).getScreenName(), "2");
		}
		file.write(json.toString());
		file.close();
	}

}
