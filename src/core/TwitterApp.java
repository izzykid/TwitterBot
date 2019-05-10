package core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import input.ButtonManager;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import utils.Utils;

public class TwitterApp implements Runnable {
	
	private ButtonManager buttonManager;

	private Thread thread;
	private boolean running = false;
	private long timer;

	private static Twitter twitter;
	private static TwitterFactory twitterFactory;
	
	public TwitterApp(){
		buttonManager = new ButtonManager();
	}

	/**
	 * Initialize data members
	 * @throws IOException 
	 */
	public void init() {
		
	}
	
	public ButtonManager getButtonManager(){
		return buttonManager;
	}
	
	public static Twitter getTwitter() {
		return twitter;
	}

	public static void setTwitter(Twitter twitter) {
		TwitterApp.twitter = twitter;
	}

	public static TwitterFactory getTwitterFactory() {
		return twitterFactory;
	}

	public static void setTwitterFactory(TwitterFactory twitterFactory) {
		TwitterApp.twitterFactory = twitterFactory;
	}

	@SuppressWarnings("unchecked")
	private void tick() throws TwitterException, IOException, ParseException, InterruptedException {
		if(!new File("res/TargetedUsers.json").exists()) {
			FileWriter file = new FileWriter("res/TargetedUsers.json");
			JSONObject obj = new JSONObject();
			obj.put("username", "2");
			file.write(obj.toString());
			file.close();
		}
		if(!new File("res/Influencers.json").exists()) {
			FileWriter file = new FileWriter("res/Influencers.json");
			JSONObject obj = new JSONObject();
			obj.put("username", "2");
			file.write(obj.toString());
			file.close();
		}
		if(!new File("res/APIKeys.json").exists()) {
			FileWriter file = new FileWriter("res/APIKeys.json");
			JSONObject obj = new JSONObject();
			obj.put("username", "2");
			file.write(obj.toString());
			file.close();
		}
		if(!new File("res/Cookies.txt").exists()) {
			FileWriter file = new FileWriter("res/Cookies.txt");
			file.write("");
			file.close();
		}
		try {
			Launcher.setLblHello("Logged into " + Utils.getLogInInfo()[0]);
		}
		catch(Exception e) {
			
		}
		buttonManager.tick();
	}
	
	
	@Override
	public void run() {
		
		init();
		
		int tps = 1;
		double timePerTick = 1000000000 / tps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		
		timer = 0;
		
		while(running){
			
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){	
				try {
					tick();
				} catch (TwitterException | IOException | ParseException | InterruptedException e) {
					e.printStackTrace();
				}
				delta--;
			}
			
			if(timer >= 1000000000){
				timer = 0;
			}
		}
		stop();
	}

	public synchronized void start(){
		// If already running do not start again
		if(running){
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		// If already stopped do not stop again
		if(!running){
			return;
		}
		running = false;
		try{
			thread.join();
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
