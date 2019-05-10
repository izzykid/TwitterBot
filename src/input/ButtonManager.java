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
import interact.Login;
import twitter4j.TwitterException;
import utils.Utils;


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
		if((Utils.getLogInInfo() == null) && (postTweet | grabTargets | updateInfluencers | followUsers)) {
			postTweet = false;
			grabTargets = false;
			updateInfluencers = false;
			followUsers = false;
			Launcher.setWarningLabel("You must log in first");
			return;
		}
		
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
				Launcher.setWarningLabel("Max Followers and Pause time should be valid integers above 0");
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
				Launcher.setWarningLabel("#'s should be above 0");
			}
		}
		if(postTweet) {
			System.out.println("Post tweet");
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
				Launcher.setWarningLabel("#'s should be above 0");
			}
		}
		if(login) {
			String username = Launcher.getUserTxtField().getText();
			String password = new String(Launcher.getPasswordTxtField().getPassword());
			new Login(username, password);
			Launcher.setUserTxtField("");
			Launcher.setPasswordTxtField("");
//			Launcher.warningLabel.setText("Invalid Login!!!!!!!");
			login = false;
		}
	}
}