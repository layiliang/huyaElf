package com.lyl.webElf.utils;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class DriverUtil {

	private static ThreadLocal<WebDriver> localDriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<Map<String,String>> localHandles = new ThreadLocal<Map<String,String>>();
	private static WebDriver driver;

	public static WebDriver initDriver(DriverCreater driverCreater) {
		if(localDriver.get() == null){
			driver = driverCreater.createDriver();
			localDriver.set(driver);
		}
		return localDriver.get();
	}

	public static WebDriver initDriver() {
		if(localDriver.get() == null){
			driver = initDriver(new ChromeDriverCreater());
			localDriver.set(driver);
		}
		return localDriver.get();
	}
		public static WebDriver initDriver(String driverClassName,String driverPathKey,String driverPathValue,Map<String,Object> prefs) {
		if (localDriver.get() == null) {
			//System.setProperty("webdriver.chrome.driver", "E:/autoTest/chromedriver_win32/chromedriver.exe");
			//System.setProperty("phantomjs.binary.path", "E:/autoTest/phantomjs-2.1.1-windows/bin/phantomjs.exe");
			System.setProperty(driverPathKey,driverPathValue);
			try {
				ChromeOptions options = new ChromeOptions();
				options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				prefs.put("profile.managed_default_content_settings.images", 2);
				/*prefs.put("profile.managed_default_content_settings.media_stream", 2);
				prefs.put("profile.managed_default_content_settings.media_stream_camera", 2);
				prefs.put("profile.managed_default_content_settings.media_stream_mic", 2);*/
				
				options.setExperimentalOption("prefs", prefs);
				@SuppressWarnings("rawtypes")
				Class clz = Class.forName(driverClassName);
				Constructor<WebDriver> dc2 = clz.getDeclaredConstructor(ChromeOptions.class);
				driver = (WebDriver) dc2.newInstance(options);
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				System.out.println(driver);
			} catch (Exception e) {
				e.printStackTrace();
			}
			localDriver.set(driver);
		}
		return localDriver.get();
	}

	public static void open(String url) {
		driver.get(url);
	}


	// TODO
	/**
	 * 转到新打开的窗口，应再加些判断
	 */
	public static void switchToNewWindow() {
		WebDriver driver = getDriver();
		Set<String> newHandles = getDriver().getWindowHandles();
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
