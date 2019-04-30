package interact;

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class SeleniumCall {

	protected String username;
	protected String password;
	
	public SeleniumCall(String username, String password) {
		this.username = username;
		this.password = password;
		
		// Prepares WebDriver to use ChromeDriver
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		
		// Creates new WebDriver object
		WebDriver driver = new ChromeDriver();
//		Cookie cookie = new Cookie("username", "password");
//		driver.manage().addCookie(cookie);
		
		Set<Cookie> cookies = driver.manage().getCookies();
		for(Cookie cookie : cookies) {
			System.out.println(cookie);
		}
	}
}
