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
import apiCalls.TestCall;
import apiCalls.UpdateInfluencers;
import interact.FollowUsers;
import twitter4j.TwitterException;


public class ButtonManager {

	public boolean followTargets, updateInfluencers, test;
	
	public ButtonManager() {
		followTargets = false;
		updateInfluencers = false;
		test = false;
	}
	
	public void tick() throws TwitterException, IOException, ParseException, InterruptedException {
		if(followTargets) {
			System.out.println("Follow Users");
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader("res/Influencers.json"));
			JSONObject targetUsers = (JSONObject) obj;
			Iterator it = targetUsers.entrySet().iterator();
			ArrayList<String> IDs = new ArrayList<String>();
			while(it.hasNext()) {
				Entry<String, String> pair = (Entry<String, String>) it.next();
				IDs.add(pair.getKey());
				System.out.println(IDs.size() + ": " + IDs);
			}
			followTargets = false;
			// Grabs 1-3 random influencers to grab followers from
			double rand = Math.random();
			int targetAmount;
			if(rand > 0.666666)
				targetAmount = 1;
			else if(rand < 0.333333)
				targetAmount = 2;
			else
				targetAmount = 3;
			
			// Select the target amount of influencers and select them at random
			HashSet<String> targetInfluencers = new HashSet<String>();
			while(targetInfluencers.size() < targetAmount) {
				targetInfluencers.add(IDs.get((int) Math.floor(Math.random() * IDs.size())));
			}
			// Grab followers from each of the randomly selected influencers
			for(String ID : targetInfluencers) { 
				new GrabFollowers(Long.parseLong(ID), 25);
				
//				new GrabFollowers(50);
			}
//			new GrabFollowers(757074558373343232l, 25); 
		}
		if(updateInfluencers) {
			System.out.println("Update Influencers");
			updateInfluencers = false;
			new UpdateInfluencers();
		}
		if(test) {
			System.out.println("Test Call");
			test = false;
			ArrayList<String> targets = new ArrayList<String>();
			targets.add("kyle_foster2");
			new FollowUsers(targets, "steezymemes", "twitterTesting");
		}
//		if(listItem){
//			eBayApplicationMethods.listItemToEbay();
//			listItem = false;
//		}
	}
}
