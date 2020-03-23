package com.lyl.webElf.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyl.webElf.base.context.DigBeanWebDriverContext;
import com.lyl.webElf.domain.Treasure;
import com.lyl.webElf.utils.DriverUtil;
import com.lyl.webElf.utils.FileUtil;
/**
 * 先试验一个账号，直接点击链接进入挖豆直播间。
 * 后期应将搜索直播间和进入直播间分开。搜索到直播间后存进list或数据库。多个账号同时从list或数据库中
 * 取出直播间列表，分别进入后挖宝
 * @author zl
 *
 */
@Service
public class DigBeanService {
	//@Autowired
	//private DigBeanWebDriverContext digBeanWebDriverContext; //此driver查找元素的时候默认不做任何等待。
	@Autowired
	private HuyaManageService huyaManageService ;
	private Set<Treasure> treasures = new HashSet<Treasure>();
	private final String DEFAULT_URL = "https://www.huya.com/11342412";
	Logger logger = Logger.getLogger(DigBeanService.class);
	public void open() throws Exception {
		WebDriver driver = DriverUtil.getDefaultDriver();
		
		Map<String,String> handles = DriverUtil.getDefaultHandles();
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		for(String handle : driver.getWindowHandles()){
			driver.switchTo().window(handle);
			if(driver.getCurrentUrl().equals(DEFAULT_URL)){
				handles.put(DEFAULT_URL, handle);
				break;
			}
		}

		String path = this.getClass().getClassLoader().getResource("templates/logTreasureJs.js").getPath();
		String logTreasureJs = FileUtil.getTemplateContent(path);
		driver_js.executeScript(logTreasureJs);
		//driver.get(DEFAULT_URL);
		//huyaManageService.login();
		//handles.put(DEFAULT_URL, driver.getWindowHandle());
		//每当有挖豆机会的时候，判断重复，并记录主播名字，大哥名字，礼物名称。
		while(true){
			Thread.sleep(1111);
			String test = "console.log('aaa')";
			driver_js.executeScript(test);
			List<WebElement> huyayihao = driver.findElements(By.className("huyayihao"));
			//其他宝藏
			List<WebElement> otherTreasures = driver.findElements(By.className("player-banner-gift"));
			for(WebElement we : huyayihao){
				logger.info(we.getText());
				List<WebElement> info = we.findElements(By.tagName("c4"));
				Treasure treasure = new Treasure();
				logger.info(info.get(0).getText());
				logger.info(info.get(1).getText());
				treasure.setBoss(info.get(0).getText());
				treasure.setHost(info.get(1).getText());
				treasure.setType("虎牙一号");
				
				if(!treasures.contains(treasure)){
					String className = we.getAttribute("class");
					logger.info(className);
					String clickJs = "$('."+ className + "').click()";
					driver_js.executeScript(clickJs);
					Thread.sleep(111);
					DriverUtil.switchToNewWindow(driver,handles);
					String hostUrl = driver.getCurrentUrl();
					handles.put(hostUrl, driver.getWindowHandle());
					treasure.setHostUrl(hostUrl);
					String handle = driver.getWindowHandle();
					treasure.setHandle(handle);
					treasures.add(treasure);
					//挖豆的js脚本，挖豆、关闭
					path = this.getClass().getClassLoader().getResource("templates/digBean.js").getPath();
					String digBeanJs = FileUtil.getTemplateContent(path);
					driver_js.executeScript(digBeanJs);
					driver.switchTo().window(handles.get(DEFAULT_URL));
				}
			}
			for(WebElement we :otherTreasures){
				logger.info(we.getText());
				String text = we.getText();
				String type=null;
				if(text.indexOf("藏宝图")>0 ||text.indexOf("空投")>0 ||text.indexOf("战神号")>0 ||text.indexOf("钞票枪")>0){
					type = "藏宝图";
				}else if(text.indexOf("空投")>0){
					type = "虎牙一号";
				}else if(text.indexOf("战神号")>0){
					type = "战神号";
				}else if(text.indexOf("钞票枪")>0){
					type = "钞票枪";
				}
				if(type!=null){
					List<WebElement> info = we.findElements(By.tagName("c1"));
					Treasure treasure = new Treasure();
					logger.info(info.get(0).getText());
					logger.info(info.get(1).getText());
					treasure.setBoss(info.get(0).getText());
					treasure.setHost(info.get(1).getText());
					treasure.setType(type);
					if(!treasures.contains(treasure)){
						//we.click();
						String className = we.getAttribute("class");
						logger.info(className);
						String clickJs = "$('."+ className + "').click()";
						driver_js.executeScript(clickJs);
						Thread.sleep(111);
						DriverUtil.switchToNewWindow(driver,handles);
						String hostUrl = driver.getCurrentUrl();
						handles.put(hostUrl, driver.getWindowHandle());
						treasure.setHostUrl(hostUrl);
						String handle = driver.getWindowHandle();
						treasure.setHandle(handle);
						treasures.add(treasure);
						//挖豆的js脚本，挖豆、关闭
						path = this.getClass().getClassLoader().getResource("templates/digBean.js").getPath();
						String digBeanJs = FileUtil.getTemplateContent(path);
						driver_js.executeScript(digBeanJs);
						driver.switchTo().window(handles.get(DEFAULT_URL));
					}
				}
			}
		}
	}

	public void close() throws InterruptedException {
		WebDriver driver = DriverUtil.getDefaultDriver();
		Map<String,String> handles = DriverUtil.getDefaultHandles();
		Collection<Entry<String, String>> oldHandles = handles.entrySet();
		while(true){
			Set<Treasure> tempTreasures = new HashSet<Treasure>();
			Map<String,String> tempHandles = new HashMap<String,String>();
			Thread.sleep(1111);
			Set<String> handleList = driver.getWindowHandles();
			for(String handle : handleList){
				for(Treasure treasure:treasures){
					if(treasure.getHandle().equals(handle)){
						tempTreasures.add(treasure);
					}
				}
				for(Entry<String, String> handleEntry:oldHandles){
					if(handleEntry.getValue().equals(handle)){
						tempHandles.put(handleEntry.getKey(), handleEntry.getValue());
					}
				}
			}
			treasures = tempTreasures;
			handles = tempHandles;
		}
	}

}
