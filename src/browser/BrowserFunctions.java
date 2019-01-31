package browser;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import utils.Utils;

public class BrowserFunctions {
	
	/**
	 * Logs into twitter.com using ChromeDriver. Returns the WebDriver object so that it can be
	 * used by other methods and can later be closed to prevent memory leaks.
	 * @param username - username that will be entered when logging into twitter
	 * @param password - password that will be entered when logging into twitter
	 * @return WebDriver - the WebDriver object that is associated with the browser used to log into twitter
	 */
	public static WebDriver logIn(String username, String password){
		// Prepares WebDriver to use ChromeDriver
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		
		// Creates new WebDriver object
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.twitter.com/");
		
		// Logs in
		driver.findElement(By.xpath("//*[@id=\"doc\"]/div/div[1]/div[1]/div[2]/div[2]/div/a[2]")).click();
		Utils.waitUntilClickable("//*[@id=\"page-container\"]/div/div[1]/form/fieldset/div[1]/input", driver);
		driver.findElement(By.xpath("//*[@id=\"page-container\"]/div/div[1]/form/fieldset/div[1]/input")).sendKeys(username);
		driver.findElement(By.xpath("//*[@id=\"page-container\"]/div/div[1]/form/fieldset/div[2]/input")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"page-container\"]/div/div[1]/form/div[2]/button")).click();
		
		return driver;
	}
	
	public static WebDriver registerBot(String name, String email, String password, String emailPassword){
		// Prepares WebDriver to use ChromeDriver
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		
		// Creates new WebDriver object
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.twitter.com/");
		
		// Wait until sign in is clickable then click
		Utils.waitUntilClickable("//*[@id=\"doc\"]/div/div[1]/div[1]/div[2]/div[2]/div/a[1]", driver);
		driver.findElement(By.xpath("//*[@id=\"doc\"]/div/div[1]/div[1]/div[2]/div[2]/div/a[1]")).click();
		// Wait until "Use email instead" is clickable then click TODO: Account for variant
		Utils.waitUntilClickable("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[4]", driver);
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[4]")).click();
		
		// Enter name and email
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[2]/label/div/div/input")).sendKeys(name);
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[3]/label/div/div/input")).sendKeys(email);
		
		// Click on name, wait for next to be clickable, then click next
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[2]/label/div/div/input")).click();
	
		// TODO: Remove Thread.sleep and replace it with something
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div/div/div[3]/div/div")).click();
	
		// Wait until "Sign Up" is clickable then click
		Utils.waitUntilClickable("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div[5]/div", driver);
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div[5]/div")).click();
		
		// TODO: Go to email then enter verification code
		
		// Opens new tab to verify email
//		driver.findElement(By.xpath("/html/body")).sendKeys(Keys.CONTROL + "t");
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get("https://www.gmail.com/");
		
		// Enters email
		driver.findElement(By.xpath("//*[@id=\"identifierId\"]")).sendKeys(email.substring(0, email.indexOf('+')) + email.substring(email.indexOf('@')));
		// Clicks next
		////*[@id="identifierNext"]/content
		driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/content")).click();
		// Enters email password
		Utils.waitUntilVisible("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/div/form/content/section/div/content/div[1]/div/div[1]/div/div[1]", driver);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/div/form/content/section/div/content/div[1]/div/div[1]/div/div[1]/input")).sendKeys(emailPassword);
		// Clicks next
		driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/div[2]")).click();
		
		// TODO: Make the program search through emails to find the verification email
		Utils.waitUntilClickable("//*[@id=\":2u\"]", driver);
		driver.findElement(By.xpath("//*[@id=\":2u\"]")).click();
		driver.findElement(By.xpath("//*[@id=\":ij\"]/div[1]/div[2]/div/table/tbody/tr[2]/td")).click();
		
		
		
		
		return driver;
	}
}
