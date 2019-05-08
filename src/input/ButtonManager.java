package input;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import apiCalls.GrabFollowers;
import apiCalls.TweetReposter;
import apiCalls.UpdateInfluencers;
import core.Launcher;
import interact.FollowUsers;
import twitter4j.TwitterException;


public class ButtonManager {

	public boolean grabTargets, updateInfluencers, followUsers, postTweet;
	
	public ButtonManager() {
		postTweet = false;
		grabTargets = false;
		updateInfluencers = false;
		followUsers = false;
	}
	
	public void tick() throws TwitterException, IOException, ParseException, InterruptedException {
		if(grabTargets) {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader("res/Influencers.json"));
			JSONObject targetUsers = (JSONObject) obj;
			Iterator<?> it = targetUsers.entrySet().iterator();
			ArrayList<String> usernames = new ArrayList<String>();
			while(it.hasNext()) {
				@SuppressWarnings("unchecked")
				Entry<String, String> pair = (Entry<String, String>) it.next();
				usernames.add(pair.getKey());
			}
			grabTargets = false;
			// Grab followers from a random influencer
			new GrabFollowers(usernames.get((int) Math.floor(Math.random() * usernames.size())),
					Integer.parseInt(Launcher.getTargetAmount().getText()));
		}
		if(updateInfluencers) {
			updateInfluencers = false;
			new UpdateInfluencers(Launcher.getInfluencerTracker().getText());
		}
		if(followUsers) {
			System.out.println("Follow Users");
			followUsers = false;
			new FollowUsers(Integer.parseInt(Launcher.getMaxFollow().getText()), Integer.parseInt(Launcher.getPauseTime().getText()));
		}
		if(postTweet) {
			new TweetReposter(Integer.parseInt(Launcher.getNumOfTweets().getText()), Integer.parseInt(Launcher.getNumOfInfluencers().getText()));
			postTweet = false;
		}
	}
}
