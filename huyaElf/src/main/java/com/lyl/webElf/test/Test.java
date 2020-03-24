package com.lyl.webElf.test;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.lyl.webElf.utils.DriverUtil;
import com.lyl.webElf.utils.PhantomjsDriverCreater;

public class Test{
	public static void main(String[] args) throws InterruptedException {

		DriverUtil.open("https://www.huya.com/11342412");
		DriverUtil.getDefaultHandles().put("aaa", DriverUtil.getDefaultDriver().getWindowHandle());
		Thread.sleep(5555);
		DriverUtil.getDefaultDriver().findElement(By.className("toAnchor")).click();
		Thread.sleep(5555);
		DriverUtil.switchToNewWindow();
		Thread.sleep(5555);
		DriverUtil.getDefaultDriver().close();
		Thread.sleep(5555);
		DriverUtil.getDefaultDriver().switchTo().window(DriverUtil.getDefaultHandles().get("aaa"));
		System.out.println(DriverUtil.getDefaultDriver().getCurrentUrl());
		
	}
}