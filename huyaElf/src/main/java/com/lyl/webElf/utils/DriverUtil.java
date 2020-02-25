package com.lyl.webElf.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;

public class DriverUtil {

	private static ThreadLocal<WebDriver> localDriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<Map<String,String>> localHandles = new ThreadLocal<Map<String,String>>();
	private static WebDriver driver;

	public static WebDriver initDriver(DriverCreater driverCreater) {
		if(localDriver.get() == null){
			driver = driverCreater.createDriver();
			driver.manage().window().maximize();
			localDriver.set(driver);
		}
		return localDriver.get();
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		DriverUtil.driver = driver;
	}

	public static WebDriver initDriver() {
		if(localDriver.get() == null){
			driver = initDriver(new ChromeDriverCreater());
			driver.manage().window().maximize();
			localDriver.set(driver);
		}
		return localDriver.get();
	}

	public static WebDriver newDriver() {
		WebDriver driver = new ChromeDriverCreater().createDriver();
		return driver;
	}

	public static WebDriver newDriver(DriverCreater driverCreater) {
		WebDriver driver = driverCreater.createDriver();
		return driver;
	}

	public static void open(String url) {
		driver.get(url);
	}


	// TODO
	/**
	 * 转到新打开的窗口，应再加些判断
	 */
	public static void switchToNewWindow() {
		Set<String> newHandles = driver.getWindowHandles();
		Collection<String> oldHandles = getHandles().values();
		for (String handle : newHandles) {
			if (!oldHandles.contains(handle)) {
				driver.switchTo().window(handle);
				break;
			}
		}
	}

	public static Map<String, String> getHandles() {
		if (localHandles.get() == null) {
			Map<String,String> handles = new HashMap<String,String>();
			localHandles.set(handles);
		}
		return localHandles.get();
	}
}
