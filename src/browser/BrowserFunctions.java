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
		driver.get("https://mail.google.com/mail/?ui=html");
		
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
		driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/content/span")).click();
		
		// Confirms HTML version of gmail
//		Utils.waitUntilClickable("//*[@id=\"maia-main\"]/form/p/input", driver);
//		driver.findElement(By.xpath("//*[@id=\"maia-main\"]/form/p/input")).click();
		
		// Opens top email
		Utils.waitUntilClickable("/html/body/table[2]/tbody/tr/td[2]/table[1]/tbody/tr/td[2]/form/table[2]/tbody/tr[1]/td[3]/a/span/b", driver);
		driver.findElement(By.xpath("/html/body/table[2]/tbody/tr/td[2]/table[1]/tbody/tr/td[2]/form/table[2]/tbody/tr[1]/td[3]/a/span/b")).click();

		// Grabs verification code
		Utils.scrollToElement("/html/body/table[2]/tbody/tr/td[2]/table[1]/tbody/tr/td[2]/table[4]/tbody/tr/td/table[7]/tbody/tr[4]/td/div/div/table/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr/td[2]/table/tbody/tr[10]/td", driver);
		Utils.waitUntilVisible("/html/body/table[2]/tbody/tr/td[2]/table[1]/tbody/tr/td[2]/table[4]/tbody/tr/td/table[7]/tbody/tr[4]/td/div/div/table/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr/td[2]/table/tbody/tr[10]/td", driver);
		String verificationCode = driver.findElement(By.xpath("/html/body/table[2]/tbody/tr/td[2]/table[1]/tbody/tr/td[2]/table[4]/tbody/tr/td/table[7]/tbody/tr[4]/td/div/div/table/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr/td[2]/table/tbody/tr[10]/td")).getText();
		System.out.println(verificationCode);
		
		// Closes the gmail tab
		driver.switchTo().window(tabs.get(0));
		
		// Enters verification code and hits next
		Utils.waitUntilVisible("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div[3]/label/div/div/input", driver);
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div[3]/label/div/div/input")).sendKeys(verificationCode);
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div/div/div[3]/div/div/span")).click();
		//*[@id="react-root"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div[3]/label/div/div/input
		
		// Creates the password
		Utils.waitUntilVisible("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[3]/div[1]/label/div/div/input", driver);
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[3]/div[1]/label/div/div/input")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div/div/div[3]/div/div/span")).click();
		
		// TODO: Add profile pictures here?
		// Clicks skip adding profile picture for now
		Utils.waitUntilClickable("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div/div/div[3]/div/div/span", driver);
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div/div/div[3]/div/div/span")).click();
		
		// TODO: Add a bio here?
		// Clicks skip adding a bio for now
		Utils.waitUntilClickable("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div/div/div[3]/div/div/span", driver);
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div/div/div[3]/div/div/span")).click();
		
		// Clicks on do not find friends by contacts
		Utils.waitUntilClickable("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div[2]/div[2]/div/span", driver);
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div[2]/div[2]/div/span")).click();
		
		// Skips finding categories that you are interested in
		Utils.waitUntilClickable("//*[@id\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div[2]/div[2]/div/span", driver);
		driver.findElement(By.xpath("//*[@id\"react-root\"]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div[2]/div[2]/div/span")).click();
		
		return driver;
	}
}
