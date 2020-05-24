package com.lyl.webElf.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import com.lyl.webElf.base.context.DriverContext;

public class DriverUtil {

	private static ThreadLocal<WebDriver> localDriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<Map<String,String>> localHandles = new ThreadLocal<Map<String,String>>();
	private static WebDriver defaultDriver = newDefaultDriver();
	private static Map<String,String> defaultHandles =new HashMap<String,String>() ;
	
	 //@Value("${demo.defaultWebDriverCreater}")
   // public static String defaultWebDriverCreater;
	 
	 //@Autowired
	   // private static Environment environment;
/**/


	/*public static WebDriver initDriver(DriverCreater driverCreater) {
		if(localDriver.get() == null){
			driver = driverCreater.createDriver();
			driver.manage().window().maximize();
			localDriver.set(driver);
		}
		return localDriver.get();
	}
	public static WebDriver initDriver() {
		if(localDriver.get() == null){
			driver = initDriver(new ChromeDriverCreater());
			driver.manage().window().maximize();
			localDriver.set(driver);
		}
		return localDriver.get();
	}*/

	public static Map<String, String> getDefaultHandles() {
		return defaultHandles;
	}

	public static void setDefaultHandles(Map<String, String> defaultHandles) {
		DriverUtil.defaultHandles = defaultHandles;
	}

	public static WebDriver newDefaultDriver() {
		try {
			//Class<?> cls = Class.forName(environment.getProperty("demo.defaultWebDriverCreater") );
			//DriverCreater driverCreater =  (DriverCreater) cls.newInstance();
			DriverCreater driverCreater =  new ChromeDriverCreater();
			//DriverCreater driverCreater =  new ChromeHeadLessDriverCreater();
			//DriverCreater driverCreater =  new PhantomjsDriverCreater();
			WebDriver driver = driverCreater.createDriver();
			return driver;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static WebDriver getDefaultDriver() {
		return defaultDriver;
	}

	public static void setDefaultDriver(WebDriver defaultDriver) {
		DriverUtil.defaultDriver = defaultDriver;
	}

	public static WebDriver newDriver(DriverCreater driverCreater) {
		WebDriver driver = driverCreater.createDriver();
		return driver;
	}

	public static void open(DriverContext driverContext, String url) {
		WebDriver driver = driverContext.getDriver();
		String jsStr = "window.open('"+url+"')";
		JsUtils.execute(driver, jsStr);
	}


	// TODO
	/**
	 * 转到新打开的窗口，应再加些判断
	 */
	public static void switchToNewWindow(DriverContext driverContext) {
		WebDriver driver = driverContext.getDriver();Map<String,String> handles = driverContext.getHandles();
		Set<String> newHandles = driver.getWindowHandles();
		Collection<String> oldHandles = handles.values();
		for (String handle : newHandles) {
			if (!oldHandles.contains(handle)) {
				driver.switchTo().window(handle);
				break;
			}
		}
	}

	// TODO
	/**
	 * 转到新打开的窗口，应再加些判断
	 */
	public static void switchToNewWindow() {
		Set<String> newHandles = defaultDriver.getWindowHandles();
		Collection<String> oldHandles = getDefaultHandles().values();
		for (String handle : newHandles) {
			if (!oldHandles.contains(handle)) {
				defaultDriver.switchTo().window(handle);
				break;
			}
		}
	}

	public static void get(String url) {
		defaultDriver.get(url);
		
	}
	
	/*public static Map<String, String> getHandles() {
		System.out.println(Thread.currentThread().getName());
		if (localHandles.get() == null) {
			Map<String,String> handles = new HashMap<String,String>();
			localHandles.set(handles);
		}
		return localHandles.get();
	}*/
}
