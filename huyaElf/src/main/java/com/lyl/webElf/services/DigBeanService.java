package com.lyl.webElf.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

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
	private Set<String> treasureHosts = new HashSet<String>();
	private final String DEFAULT_URL = "https://www.huya.com/11342412";
	Logger logger = Logger.getLogger(DigBeanService.class);
	List<Map<String, String>> allHarvest = new ArrayList<Map<String,String>>();
	List<Map<String, String>> successHarvest = new ArrayList<Map<String,String>>();
	int allHarvestNumGold=0;
	int allHarvestNumSilver=0;
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
		path = this.getClass().getClassLoader().getResource("templates/delVideo.js").getPath();
		String delVideoJs = FileUtil.getTemplateContent(path);
		driver_js.executeScript(delVideoJs);
		path = this.getClass().getClassLoader().getResource("templates/digBean.js").getPath();
		String digBeanJs = FileUtil.getTemplateContent(path);
		//driver.get(DEFAULT_URL);
		//huyaManageService.login();
		//handles.put(DEFAULT_URL, driver.getWindowHandle());
		//每当有挖豆机会的时候，判断重复，并记录主播名字，大哥名字，礼物名称。
		long start = System.currentTimeMillis();
		int index = 1;
		int count = 0 ; 
		while(true){
			Thread.sleep(1000);
			index++;
			try {
				List<WebElement> newTreasureHosts =  new ArrayList<WebElement>();
				//logger.info("开始查找：" + new Date());
				//虎牙一号1
				List<WebElement> huyayihao1 = driver.findElements(By.className("huyayihao"));
				//虎牙一号2
				List<WebElement> huyayihao2 = driver.findElements(By.className("player-banner-text-20269"));
				//藏宝图
				List<WebElement> cangbaotu = driver.findElements(By.className("player-banner-text-12"));
				//战神号
				List<WebElement> zhanshenhao1 = driver.findElements(By.className("player-banner-text-20303"));
				//战神号
				List<WebElement> zhanshenhao2 = driver.findElements(By.className("player-banner-text-20291"));
				if(huyayihao1!=null && huyayihao1.size()>0){
					newTreasureHosts.add(huyayihao1.get(0));
				}
				if(huyayihao2!=null && huyayihao2.size()>0){
					newTreasureHosts.add(huyayihao2.get(0));
				}
				if(cangbaotu!=null && cangbaotu.size()>0){
					newTreasureHosts.add(cangbaotu.get(0));
				}
				if(zhanshenhao1!=null && zhanshenhao1.size()>0){
					newTreasureHosts.add(zhanshenhao1.get(0));
				}
				if(zhanshenhao2!=null && zhanshenhao2.size()>0){
					newTreasureHosts.add(zhanshenhao2.get(0));
				}
				for(int i = 0 ; i < newTreasureHosts.size();i++){
					WebElement ele = newTreasureHosts.get(i);
					String hostByshort = ele.getAttribute("innerText").split(" ")[2];
					if(hostByshort.indexOf("...")>0){
						hostByshort = hostByshort.replace("...", "");
					}
					if(!treasureHosts.contains(hostByshort)){
						count++;
						String className = ele.getAttribute("class");
						String clickJs = "$('."+ className + "').click()";
						driver_js.executeScript(clickJs);
						treasureHosts.add(hostByshort);
						DriverUtil.switchToNewWindow();
						driver_js.executeScript(digBeanJs);	
						handles.put(hostByshort, driver.getWindowHandle());
						logger.info("第"+count + "次发现宝藏");
						logger.info("主播"+hostByshort);
						logger.info("treasureHosts数目"+treasureHosts.size()+"，treasureHosts列表:"+treasureHosts);
						logger.info("handles数目"+handles.size()+"，handles列表:"+handles);
					}
				}
				long time = System.currentTimeMillis()-start;
				if(time>60000){
					start = System.currentTimeMillis();
					logger.info(index + " : " + time);
					digBean();
				}
				if(!driver.getWindowHandle().equals(handles.get(DEFAULT_URL))){
					driver.switchTo().window(handles.get(DEFAULT_URL));
				}
			} catch (StaleElementReferenceException e) {
				logger.info("元素不存在于页面了");
			}
			
		}
	}

	public void digBean() throws Exception {
		WebDriver driver = DriverUtil.getDefaultDriver();
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		Map<String,String> handles = DriverUtil.getDefaultHandles();
		Set<String> handleList = driver.getWindowHandles();
		for(String handle : handleList){
			if(!handle.equals(handles.get(DEFAULT_URL))){
				driver.switchTo().window(handle);
				String host = driver.findElement(By.className("host-name")).getAttribute("innerText");
				logger.info("切换到  " + host + "的直播间");
				if(driver.findElements(By.className("digBeanFlg"))==null || driver.findElements(By.className("digBeanFlg")).size()==0){
					String path = this.getClass().getClassLoader().getResource("templates/digBean.js").getPath();
					String digBeanJs = FileUtil.getTemplateContent(path);
					driver_js.executeScript(digBeanJs);	
				}
				String closeFlgJs = "return closeFlg;";
				long closeFlg =  (long) driver_js.executeScript(closeFlgJs);
				int treasureSize = driver.findElements(By.className("liveRoom_treasureChest")).size();
				String getHarvest = "return harvest;";
				List<Map<String, String>> curHarvest = (List<Map<String, String>>) driver_js
						.executeScript(getHarvest);
				if(closeFlg==1 || treasureSize==0){
					for(String hostByshort : treasureHosts){
						if(host.indexOf(hostByshort)>=0 || hostByshort.indexOf(host)>=0){
							treasureHosts.remove(hostByshort);
							if(curHarvest.size()==0){
								logger.info("挖豆结果不应该是0");
							}
							for(Map<String, String> digResult : curHarvest){

								//恭喜你获得银豆1000！
								String digResultStr = digResult.get("digResult");
								logger.info("挖豆结果:" + digResultStr);
								if(digResultStr.indexOf("手气不佳")==-1){
									successHarvest.add(0,digResult);
									int num = Integer.parseInt(digResultStr.substring(7, digResultStr.length()-1));
									if(digResultStr.indexOf("银豆")>=0){
										allHarvestNumSilver+=num;
									}else{
										allHarvestNumGold+=num;
									}
								}
							}
							allHarvest.addAll(0, curHarvest);
							//allHarvest.addAll(curHarvest);
							logger.info("closeFlg:"+closeFlg);
							logger.info("treasureSize:"+treasureSize);
							logger.info(host + "的直播间挖豆已经结束");
							logger.info("此页面挖豆次数 : " + curHarvest.size());
							logger.info("------此页面挖豆结果 : " + curHarvest);
							logger.info("总挖豆次数 : " + allHarvest.size());
							logger.info("总挖豆结果 : " + allHarvest);
							logger.info("成功挖豆次数 : " + successHarvest.size());
							logger.info("成功挖豆的结果 : " + successHarvest);
							logger.info("总挖豆数 银: " + allHarvestNumSilver);
							logger.info("总挖豆数 金: " + allHarvestNumGold);
							driver.close();
							handles.remove(hostByshort);
							logger.info("关闭页面 host : " + host);
							logger.info("treasureHosts数目"+treasureHosts.size()+"，treasureHosts列表:"+treasureHosts);
							logger.info("handles数目"+handles.size()+"，handles列表:"+handles);
							break;
						}
					}
				}else{
					logger.info(host + "的直播间正在挖豆");
					logger.info("还有" +treasureSize +"个宝藏待挖");
					logger.info("当前挖豆结果 : " + curHarvest);
				}
				logger.info("---------------------------------------------------");
			}
		}
		driver.switchTo().window(handles.get(DEFAULT_URL));
	}
}
