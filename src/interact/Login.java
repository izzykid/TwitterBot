package interact;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;

import core.Launcher;
import utils.Utils;

public class Login extends SeleniumCall {
	
	public Login(String username, String password) throws IOException {
		
		System.out.println("username: " + username + " password: " + password);
		driver.get("https://www.twitter.com/" + username);
		
		try {
			// Log in
			driver.get("https://www.twitter.com/" + username);
			Utils.waitUntilVisible("//*[@id=\"signin-dropdown\"]", driver, 3);
			driver.findElement(By.xpath("//*[@id=\"signin-dropdown\"]/div[3]/form/div[1]/input")).sendKeys(username);
			driver.findElement(By.xpath("//*[@id=\"signin-dropdown\"]/div[3]/form/div[2]/input")).sendKeys(password);
			driver.findElement(By.xpath("//*[@id=\"signin-dropdown\"]/div[3]/form/input[1]")).click();
		}
		catch(Exception e) {
			try {
				driver.findElement(By.xpath("//*[@id=\"signin-link\"]/span[1]")).click();
				driver.findElement(By.xpath("//*[@id=\"signin-dropdown\"]/div[3]/form/div[1]/input")).sendKeys(username);
				driver.findElement(By.xpath("//*[@id=\"signin-dropdown\"]/div[3]/form/div[2]/input")).sendKeys(password);
				driver.findElement(By.xpath("//*[@id=\"signin-dropdown\"]/div[3]/form/input[1]")).click();
			}
			catch(Exception e1) {
				
			}
		}
		String status = "";
		try {
			System.out.println("try1");
			int i = 0;
			A: while(true) {
				try {
					System.out.println("try2 + i: " + i);
					if(i > 50)
						break A;
					// Wait for page to load
					Utils.waitUntilVisible("/html/body", driver, 10);
					// Check all possible xpaths
					status = driver.findElement(By.xpath("//*[@id=\"page-container\"]/div[1]/div/div[2]/div/div/div[2]/div/div/ul/li[" + i + "]/div/div/span[2]/button[1]/span[1]")).getText();
					// If this says follow then the log in failed
					if(status.equals("Follow")) {
						driver.findElement(By.xpath("//*[@id=\"page-container\"]/div[1]/div/div[2]/div/div/div[2]/div/div/ul/li[" + i + "]/div/div/span[2]/button[1]")).getText();
						break A;
					}
				}
				catch(NoSuchElementException e) {
					i++;
					System.out.println(i);
				}
			}
		}
		catch(Exception e) {
			
		}
		System.out.println("status: " + status);
		String invalidLogin = "";
		try {
			invalidLogin = driver.findElement(By.xpath("//*[@id=\"message-drawer\"]/div/div/span")).getText();
		}
		catch(NoSuchElementException e) {
			
		}
		
		// Update UI based on success of login
		if(invalidLogin.equals("")) {
			Launcher.setLblHello("Logged into " + username);
			StoreLoginInformation(username, password);
		}
		else
			Launcher.setWarningLabel("Invalid login");
		
		driver.close();
	}
	
	private void StoreLoginInformation(String username, String password) throws IOException {
		// Update cookies
		BufferedWriter writer = new BufferedWriter(new FileWriter("res/Cookies.txt", false));
		Set<Cookie> cookies = driver.manage().getCookies();
		for(Cookie cookie : cookies) {
			writer.append(cookie.getName() + ";" + cookie.getValue() + ";" + cookie.getDomain() + ";" + cookie.getPath() + ";" + cookie.getExpiry() + ";" + cookie.isSecure() + "\n");
		}
		writer.close();
		
		BufferedWriter logWrite = new BufferedWriter(new FileWriter("res/LogIn.txt", false));
		logWrite.append("username:" + username + "\npassword:" + password);
		logWrite.close();
	}
}
