package core;

import bots.ValidationBot;

public class Launcher {

	public static String username = "danksauz";
	public static String password = "cantThink98";
	
	public static String username2 = "memehub420";
	public static String password2 = "cantThink98";
	public static void main(String[] args){
//		WebDriver driver = BrowserFunctions.logIn(username, password);
//		driver.quit();
//		driver = BrowserFunctions.logIn(username2, password2);
//		driver.quit();
		ValidationBot.generateValidationBotInfo("memebot", "actionapple001@gmail.com", "cantThink98", 1000, "ValidationBotInfo.txt");
		ValidationBot.registerValidationBots(2, 3, "ValidationBotInfo.txt", "monkeydicks420");
	}
}

