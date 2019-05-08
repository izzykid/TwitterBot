package interact;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import twitter4j.IDs;
import utils.StopWatch;
import utils.Utils;

public class FollowUsers extends SeleniumCall {
	
	/**
	 * Logs into twitter.com using ChromeDriver. Returns the WebDriver object so that it can be
	 * used by other methods and can later be closed to prevent memory leaks.
	 * @param targetUsers - an ArrayList of users who will be attempted to be followed
	 * @return WebDriver - the WebDriver object that is associated with the browser used to log into twitter
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws ParseException 
	 */ 
	public FollowUsers(int maxFollow, int pauseTime) throws InterruptedException, IOException, ParseException { 
		// Instantiate necessary JSON variables
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("res/TargetedUsers.json"));
		JSONObject targetUsers = (JSONObject) obj;
		Iterator it = targetUsers.entrySet().iterator();
		JSONObject followingUsers = new JSONObject();
		FileWriter file = new FileWriter("res/TargetedUsers.json");
		
		// Creates a StopWatch object to pause in between following users
		StopWatch timer = new StopWatch();
		timer.start();
		
		// Prepares WebDriver to use ChromeDriver
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		// Creates new WebDriver object
		driver = new ChromeDriver();
		// Configure cookies if Cookies.txt has already been generated
		Set<Cookie> cookies = driver.manage().getCookies();
		if(new File("res/Cookies.txt").exists()) {
			Scanner sc = new Scanner(new File("res/Cookies.txt"));
			while(sc.hasNextLine()) {
				String rawCookieData = sc.nextLine();
				String[] cookieData = rawCookieData.split(";");
				String[] date = cookieData[4].split(" ");
				if(!date[0].equals("null")) {
					String[] time = date[3].split(":");
					HashMap<String, Integer> monthMap = new HashMap<String, Integer>();
					monthMap.put("Jan", 0);
					monthMap.put("Feb", 1);
					monthMap.put("Mar", 2);
					monthMap.put("Apr", 3);
					monthMap.put("May", 4);
					monthMap.put("Jun", 5);
					monthMap.put("Jul", 6);
					monthMap.put("Aug", 7);
					monthMap.put("Sep", 8);
					monthMap.put("Oct", 9);
					monthMap.put("Nov", 10);
					monthMap.put("Dec", 11);
					@SuppressWarnings("deprecation")
					Date expiry = new Date(Integer.parseInt(date[5]), monthMap.get(date[1]), Integer.parseInt(date[2]),
							Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
					Cookie cookie = new Cookie(cookieData[0], cookieData[1], cookieData[2], cookieData[3], expiry, Boolean.parseBoolean(cookieData[5]));
					cookies.add(cookie);
				}
				else {
					Cookie cookie = new Cookie(cookieData[0], cookieData[1], cookieData[2], cookieData[3], null, Boolean.parseBoolean(cookieData[5]));
					cookies.add(cookie);
				}
			}
			sc.close();
			// Open twitter to load cookies
			driver.get("https://www.twitter.com/" + targetUsers.get(0));
			// Add all cookie objects to the WebDriver object
			for(Cookie cookie : cookies) {
				try {
					driver.manage().addCookie(cookie);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			// Refresh the browser to load the cookies
			driver.navigate().refresh();
		}
		else {
			// Log in
			driver.get("https://www.twitter.com/" + targetUsers.get(0));
			Utils.waitUntilClickable("//*[@id=\"signin-dropdown\"]/div[3]/form/div[1]/input", driver, 30);
			driver.findElement(By.xpath("//*[@id=\"signin-dropdown\"]/div[3]/form/div[1]/input")).sendKeys(Utils.getLogInInfo()[0]);
			driver.findElement(By.xpath("//*[@id=\"signin-dropdown\"]/div[3]/form/div[2]/input")).sendKeys(Utils.getLogInInfo()[1]);
			driver.findElement(By.xpath("//*[@id=\"signin-dropdown\"]/div[3]/form/input[1]")).click();
		}
		
		int count = 0;
		while(it.hasNext()) {
			System.out.println("Yes");
			if(timer.getElapsedTime() < pauseTime * 1000)
				continue;
			System.out.println("Timer");
			if(count >= maxFollow)
				break;
			System.out.println("Count");
			synchronized(this) {
				Entry<String, String> pair = (Entry<String, String>) it.next();
				// If already following this user, skip attempting to follow this user
				if(!pair.getValue().equals("0"))
					continue;
				timer.reset();
				timer.start();
				count++;
				driver.get("https://www.twitter.com/" + pair.getKey());
				// Click follow
				int i = 0;
				A: while(true) {
					try { // If the account in control isn't following already, attempt to follow
						String status = driver.findElement(By.xpath("//*[@id=\"page-container\"]/div[1]/div/div[2]/div/div/div[2]/div/div/ul/li[" + i + "]/div/div/span[2]/button[1]/span[1]")).getText();
						if(status.equals("Follow"))   
							driver.findElement(By.xpath("//*[@id=\"page-container\"]/div[1]/div/div[2]/div/div/div[2]/div/div/ul/li[" + i + "]/div/div/span[2]/button[1]")).click();
						targetUsers.put(pair.getKey(), "1");
						break A;
					}
					catch(Exception e) {
						i++;
					}
				}
			}
		}
		file.write(targetUsers.toString());
		file.close();
		
		// Update cookies
		BufferedWriter writer = new BufferedWriter(new FileWriter("res/Cookies.txt", false));
		cookies = driver.manage().getCookies();
		for(Cookie cookie : cookies) {
			writer.append(cookie.getName() + ";" + cookie.getValue() + ";" + cookie.getDomain() + ";" + cookie.getPath() + ";" + cookie.getExpiry() + ";" + cookie.isSecure() + "\n");
		}
		writer.close();
		driver.close();
	}
}