package core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import input.ButtonManager;
import twitter4j.TwitterException;

public class TwitterApp implements Runnable{
	
	private ButtonManager buttonManager;

	private Thread thread;
	private boolean running = false;
	private long timer;
	
	public TwitterApp(){
		buttonManager = new ButtonManager();
	}

	/**
	 * Initialize data members
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public void init() throws IOException{
		if(!new File("res/TargetedUsers.json").exists()) {
			FileWriter file = new FileWriter("res/TargetedUsers.json");
			JSONObject obj = new JSONObject();
			obj.put(Long.toString(Long.MAX_VALUE), "username");
			file.write(obj.toString());
			file.close();
		}
		if(!new File("res/Influencers.json").exists()) {
			FileWriter file = new FileWriter("res/Influencers.json");
			JSONObject obj = new JSONObject();
			obj.put(Long.toString(Long.MAX_VALUE), "username");
			file.write(obj.toString());
			file.close();
		}
//		try {
//			sheet = new SpreadSheet(Constants.Spread_Sheet_Path);
//			sheet.populateSheet();
//		} catch (InvalidFormatException | IOException e1) {
//			e1.printStackTrace();
//		}
	}
	
	public ButtonManager getButtonManager(){
		return buttonManager;
	}


	
	private void tick() throws TwitterException, IOException, ParseException, InterruptedException {
		buttonManager.tick();
	}
	
	
	@Override
	public void run() {
		
		try {
			init();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int fps = 60;
		double timePerTick = 1000000000 / fps;
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
