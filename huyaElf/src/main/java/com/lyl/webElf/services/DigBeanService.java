package com.lyl.webElf.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
	private Set<String> treasureHosts = new HashSet<String>();
	private final String DEFAULT_URL = "https://www.huya.com/11342412";
	Logger logger = Logger.getLogger(DigBeanService.class);
	public void open() throws Exception {
		WebDriver driver = DriverUtil.getDefaultDriver();
		//driver.get(DEFAULT_URL);
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
			List<WebElement> newTreasureHosts =  new ArrayList<WebElement>();
			try {
				//logger.info("开始查找：" + new Date());
				//虎牙一号
				List<WebElement> huyayihao = driver.findElements(By.className("huyayihao"));
				//藏宝图
				List<WebElement> cangbaotu = driver.findElements(By.className("player-banner-text-12"));
				//战神号
				List<WebElement> zhanshenhao = driver.findElements(By.className("player-banner-text-20303"));
				if(huyayihao!=null && huyayihao.size()>0){
					newTreasureHosts.add(huyayihao.get(0));
				}
				if(cangbaotu!=null && cangbaotu.size()>0){
					newTreasureHosts.add(cangbaotu.get(0));
				}
				if(zhanshenhao!=null && zhanshenhao.size()>0){
					newTreasureHosts.add(zhanshenhao.get(0));
				}
				for(int i = 0 ; i < newTreasureHosts.size();i++){
					WebElement ele = newTreasureHosts.get(i);
					logger.info("内容为" + ele.getAttribute("innerText"));
					logger.info("分解后的数组长度" + ele.getAttribute("innerText").split(" ").length);
					String host = ele.getAttribute("innerText").split(" ")[2];
					if(!treasureHosts.contains(host)){
						String className = ele.getAttribute("class");
						logger.info(className);
						String clickJs = "$('."+ className + "').click()";
						driver_js.executeScript(clickJs);
						treasureHosts.add(host);
						DriverUtil.switchToNewWindow();
						path = this.getClass().getClassLoader().getResource("templates/digBean.js").getPath();
						String digBeanJs = FileUtil.getTemplateContent(path);
						driver_js.executeScript(digBeanJs);	
						driver.switchTo().window(handles.get(DEFAULT_URL));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void digBean() throws Exception {
		WebDriver driver = DriverUtil.getDefaultDriver();
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		Map<String,String> handles = DriverUtil.getDefaultHandles();
		//Collection<Entry<String, String>> oldHandles = handles.entrySet();
		while(true){
			Thread.sleep(20000);
			Set<String> handleList = driver.getWindowHandles();
			for(String handle : handleList){
				if(!handle.equals(handles.get(DEFAULT_URL))){
					driver.switchTo().window(handle);
					if(driver.findElements(By.className("digBeanFlg"))==null || driver.findElements(By.className("digBeanFlg")).size()==0){
						String path = this.getClass().getClassLoader().getResource("templates/digBean.js").getPath();
						String digBeanJs = FileUtil.getTemplateContent(path);
						driver_js.executeScript(digBeanJs);	
					}
					if(driver.findElements(By.className("closeFlg"))!=null || driver.findElements(By.className("closeFlg")).size()!=0){
						String host = driver.findElement(By.className("host-name")).getText();
						for(String hostByshort : treasureHosts){
							if(host.indexOf(hostByshort)>=0 || hostByshort.indexOf(host)>=0){
								treasureHosts.remove(hostByshort);
								driver.close();
								break;
							}
						}
					}
				}
			}
			if(!driver.getWindowHandle().equals(handles.get(DEFAULT_URL))){
				driver.switchTo().window(handles.get(DEFAULT_URL));
			}
			Thread.sleep(1000);
		}
	}
}
