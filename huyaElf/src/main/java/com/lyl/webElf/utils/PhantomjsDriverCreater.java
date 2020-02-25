package com.lyl.webElf.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class PhantomjsDriverCreater implements DriverCreater {

	@Override
	public WebDriver createDriver() {
		System.setProperty("phantomjs.binary.path", "E:/autoTest/phantomjs-2.1.1-windows/bin/phantomjs.exe");
		WebDriver driver = new PhantomJSDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println(driver);
		return driver;
	}

}
