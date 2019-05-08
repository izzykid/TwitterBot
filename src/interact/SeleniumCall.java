package interact;

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class SeleniumCall {

	protected WebDriver driver;
	
	public SeleniumCall() {
		
		// Prepares WebDriver to use ChromeDriver
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		
		// Creates new WebDriver object
		driver = new ChromeDriver();
		
		// Adds cookies to the WebDriver object
		Set<Cookie> cookies = driver.manage().getCookies();
		for(Cookie cookie : cookies) {
			System.out.println(cookie);
		}
	}
}
