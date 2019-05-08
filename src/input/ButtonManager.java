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

	public boolean grabTargets, updateInfluencers, followUsers, postTweet, login;
	
	public ButtonManager() {
		postTweet = false;
		grabTargets = false;
		updateInfluencers = false;
		followUsers = false;
		login = false;
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
			try {
				int targetAmount = Integer.parseInt(Launcher.getTargetAmount().getText());
				if(targetAmount < 1) {
					throw new Exception();
				}
				new GrabFollowers(usernames.get((int) Math.floor(Math.random() * usernames.size())),
						targetAmount);
			}
			catch(Exception e) {
				Launcher.lblWarning.setText("Max Followers and Pause time should be valid integers above 0");
			}
		}
		if(updateInfluencers) {
			updateInfluencers = false;
			new UpdateInfluencers(Launcher.getInfluencerTracker().getText());
		}
		if(followUsers) {
			System.out.println("Follow Users");
			followUsers = false;
			try {
				int maxFollow = Integer.parseInt(Launcher.getMaxFollow().getText());
				int pauseTime = Integer.parseInt(Launcher.getPauseTime().getText());
				if(maxFollow < 1 || pauseTime < 1) {
					throw new Exception();
				}
				new FollowUsers(maxFollow, pauseTime);
			}
			catch(Exception e) {
				Launcher.lblWarning.setText("Max Followers and Pause time should be valid integers above 0");
			}
		}
		if(postTweet) {
			postTweet = false;
			try {
				int numOfInfluencers = Integer.parseInt(Launcher.getNumOfInfluencers().getText());
				int numOfTweets = Integer.parseInt(Launcher.getNumOfTweets().getText());
				if(numOfInfluencers < 1 || numOfTweets < 1) {
					throw new Exception();
				}
				new TweetReposter(numOfInfluencers, numOfTweets);
			}
			catch(Exception e) {
				Launcher.lblWarning.setText("# of Influencers & tweets should be valid integers above 0");
			}
		}
		if(login) {
			String username = Launcher.userTxtField.getText();
			String password = new String(Launcher.passwordTxtField.getPassword());
			Launcher.userTxtField.setText("");
			Launcher.passwordTxtField.setText("");
			Launcher.lblWarning.setText("Invalid Login!!!!!!!");
		}
	}
}
