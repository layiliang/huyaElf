package com.lyl.webElf.test;

import org.openqa.selenium.WebDriver;

import com.lyl.webElf.utils.DriverUtil;
import com.lyl.webElf.utils.PhantomjsDriverCreater;

public class Test{
	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = DriverUtil.newDriver(new PhantomjsDriverCreater());
		driver.get("https://www.huya.com/11342412");
		while(true){
			Thread.sleep(2222);
			
		}
	}
}