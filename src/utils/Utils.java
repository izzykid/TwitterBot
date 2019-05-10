package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils {

	public static void waitUntilVisible(String xpath, WebDriver driver, int time){
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(xpath))));
	}
	
	public static void waitUntilClickable(String xpath, WebDriver driver, int time){
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
	
	public static void scrollToElement(String xpath, WebDriver driver){
		new Actions(driver).moveToElement(driver.findElement(By.xpath(xpath)), 0, -1).perform();
	}
	
	public static String[] getLogInInfo() throws FileNotFoundException {
		if(!new File("res/LogIn.txt").exists())
			System.out.println("res/LogIn.txt is missing");
		else {
			Scanner sc = new Scanner(new File("res/LogIn.txt"));
			try {
				String username = sc.nextLine().split(":")[1].trim();
				String password = sc.nextLine().split(":")[1].trim();
				sc.close();
				if(username.length() == 0 | password.length() == 0) {
					System.out.println("Username and/ or password are missing from res/LogIn.txt");
					return null;
				}
				return new String[]{username, password};
			}
			catch(ArrayIndexOutOfBoundsException e) {
				sc.close();
				return null;
			}
		}
		return null;
	}
	
	public static boolean isAuthorized() throws IOException, ParseException {
		if(!new File("res/APIKeys.json").exists())
			System.out.println("res/APIKeys.json is missing");
		else {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader("res/APIKeys.json"));
			JSONObject keys = (JSONObject) obj;
			if(keys.containsKey(getLogInInfo()[0]))
				return true;
			else
				return false;
		}
		return false;
	}
	
	public static void setAuthorizedTrue() throws IOException {
		System.out.println("setauthtrue");
		if(!new File("res/LogIn.txt").exists())
			System.out.println("res/LogIn.txt is missing");
		else {
			// Grab and store file contents
			Scanner sc = new Scanner(new File("res/LogIn.txt"));
			String username = sc.nextLine();
			String password = sc.nextLine();
			String tempAuth = sc.nextLine();
			sc.close();
			
			// Change auth to true
			tempAuth = tempAuth.split(":")[0].trim();
			String auth = tempAuth + ":true";
			
			// Write to res/LogIn.txt
			FileWriter writer = new FileWriter("res/LogIn.txt");
			writer.write(username + "\n" + password + "\n" + auth);
			writer.close();
		}
	}
}