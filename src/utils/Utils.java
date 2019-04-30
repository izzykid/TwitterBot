package utils;

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
}