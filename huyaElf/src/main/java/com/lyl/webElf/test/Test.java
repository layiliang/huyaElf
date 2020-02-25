package com.lyl.webElf.test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.lyl.webElf.utils.DriverUtil;
import com.lyl.webElf.utils.PhantomjsDriverCreater;

public class Test {
	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "E:/autoTest/chromedriver_win32/chromedriver.exe"); ChromeOptions chromeOptions = new ChromeOptions();
        // 设置为 headless 模式 （无头浏览器）
         chromeOptions.addArguments("--headless");
         WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        //设置隐性等待时间
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		//WebDriver driver = DriverUtil.getDriver();
		driver.get("https://www.huya.com/haddis");
		System.out.println(driver.getCurrentUrl());
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait = new WebDriverWait(driver, 5);
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.className("guess-icon")));
		List<WebElement> liveList = driver.findElements(By.className("guess-icon"));
		System.out.println("liveList.size = " + liveList.size());
		driver.close();
		driver.quit();
	}
}
class Page<T>{
	 void t(T t){
		System.out.println(t);
	}
}

