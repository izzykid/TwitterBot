package input;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import apiCalls.GrabFollowers;
import apiCalls.UpdateInfluencers;
import core.Launcher;
import interact.FollowUsers;
import twitter4j.TwitterException;


public class ButtonManager {

	public boolean grabTargets, updateInfluencers, followUsers, test;
	
	public ButtonManager() {
		grabTargets = false;
		updateInfluencers = false;
		followUsers = false;
		test = false;
	}
	
	public void tick() throws TwitterException, IOException, ParseException, InterruptedException {
		if(grabTargets) {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader("res/Influencers.json"));
			JSONObject targetUsers = (JSONObject) obj;
			Iterator it = targetUsers.entrySet().iterator();
			ArrayList<String> usernames = new ArrayList<String>();
			while(it.hasNext()) {
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
//		if(test) {
//			System.out.println("Test Call");
//			followUsers = false;
//			new FollowUsers("steezymemes", "twitterTesting", 5);
//		}
		if(followUsers) {
			System.out.println("Follow Users");
			followUsers = false;
			new FollowUsers(Launcher.getInfluencerTracker().getText(), "twitterTesting",
					Integer.parseInt(Launcher.getMaxFollow().getText()), Integer.parseInt(Launcher.getPauseTime().getText()));
		}
	}
}
