package com.lyl.webElf.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeHeadLessDriverCreater implements DriverCreater {

	@Override
	public WebDriver createDriver() {
		System.setProperty("webdriver.chrome.driver", "E:/autoTest/chromedriver_win32/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("--headless");
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.managed_default_content_settings.images", 2);
		options.setExperimentalOption("prefs", prefs);
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println(driver);
		return driver;
	}
	
	public WebDriver createDriver(boolean imageAble) {
		System.setProperty("webdriver.chrome.driver", "E:/autoTest/chromedriver_win32/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		Map<String, Object> prefs = new HashMap<String, Object>();
		if(imageAble==false){
			prefs.put("profile.managed_default_content_settings.images", 2);
		}
		options.setExperimentalOption("prefs", prefs);
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println(driver);
		return driver;
	}

}
