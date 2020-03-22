package com.lyl.webElf.services;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import com.lyl.webElf.base.context.DigBeanWebDriverContext;
import com.lyl.webElf.utils.DriverUtil;
/**
 * 先试验一个账号，直接点击链接进入挖豆直播间。
 * 后期应将搜索直播间和进入直播间分开。搜索到直播间后存进list或数据库。多个账号同时从list或数据库中
 * 取出直播间列表，分别进入后挖宝
 * @author zl
 *
 */
public class DigBeanService {
	@Autowired
	private DigBeanWebDriverContext digBeanWebDriverContext; //此driver查找元素的时候默认不做任何等待。
	private final String DEFAULT_URL = "https://www.huya.com/11342412";
	public void open() throws InterruptedException {
		WebDriver driver = digBeanWebDriverContext.getDriver();

		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		driver.get(DEFAULT_URL);
		//每当有挖豆机会的时候，判断重复，并记录主播名字，大哥名字，礼物名称。
		while(true){
			Thread.sleep(1111);
			List<WebElement> huyayihao = driver.findElements(By.className("huyayihao"));
			//TODO
			//其他宝藏
			List<WebElement> others = driver.findElements(By.className("player-banner-gift"));
			for(WebElement we :huyayihao){
				we.click();
				DriverUtil.switchToNewWindow();
				//挖豆的js脚本，挖豆、关闭
				String getDigBeanJs = "setInterval(function(){$('.btn-wrap span').click();},222)";
				driver_js.executeScript(getDigBeanJs);
				driver.switchTo().window("主页");
			}
			
		}
	}

	public void digBean() throws InterruptedException {
	}

}
