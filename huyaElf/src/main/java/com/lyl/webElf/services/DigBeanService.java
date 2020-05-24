package com.lyl.webElf.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import com.lyl.webElf.base.context.DiggerDriverContext;
import com.lyl.webElf.utils.DriverUtil;
import com.lyl.webElf.utils.FileUtil;
import com.lyl.webElf.utils.SystemUsageUtil;
/**
 * 先试验一个账号，直接点击链接进入挖豆直播间。
 * 后期应将搜索直播间和进入直播间分开。搜索到直播间后存进list或数据库。多个账号同时从list或数据库中
 * 取出直播间列表，分别进入后挖宝
 * @author zl
 *
 */
@Service
public class DigBeanService {
	//private Map<String,String> treasureHosts = new HashMap<String,String>();
	private Set<String> treasureHosts = new HashSet<String>();
	public  Set<String> treasureUrls = ConcurrentHashMap.newKeySet();
	private final String MAIN_URL = "https://www.huya.com/bdcmovie";
	private final String DIG_URL = "https://www.huya.com/bdcmovie";
	Logger logger = Logger.getLogger(DigBeanService.class);
	//List<Map<String, String>> allHarvest = new ArrayList<Map<String,String>>();

	public void find(DiggerDriverContext digDriverContext) {
		WebDriver driver = digDriverContext.getDriver();
		Map<String,String> handles = digDriverContext.getHandles();
		try{
			List<WebElement> newTreasureHosts =  new ArrayList<WebElement>();
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
					String className = ele.getAttribute("class");
					String clickJs = "$('."+ className + "').click()";
					((JavascriptExecutor) driver).executeScript(clickJs);
					
					treasureHosts.add(hostByshort);
					DriverUtil.switchToNewWindow(digDriverContext);
					handles.put(hostByshort, driver.getWindowHandle());
					treasureUrls.add(driver.getCurrentUrl());
					logger.info("主播"+hostByshort);
					logger.info("treasureHosts数目"+treasureHosts.size()+"，treasureHosts列表:"+treasureHosts);
				}
			}
		}catch (StaleElementReferenceException e) {
			logger.debug("元素不存在于页面了");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void refresh(DiggerDriverContext digDriverContext) throws Exception {
		WebDriver driver = digDriverContext.getDriver();
		Map<String,String> handles = digDriverContext.getHandles();
		Set<String> handleList = driver.getWindowHandles();
		for(String handle : handleList){
			driver.switchTo().window(handle);
			driver.navigate().refresh();
			if(handle.equals(handles.get(MAIN_URL))){
				Thread.sleep(30000);
				delVideoJs(driver);
			}else{
				Thread.sleep(10000);
				digBeanJs(driver);
			}
		}
	}
	public void  digDriverRefresh(DiggerDriverContext digDriverContext) throws Exception {
		WebDriver driver = digDriverContext.getDriver();
		Set<String> handleList = driver.getWindowHandles();
		for(String handle : handleList){
			driver.switchTo().window(handle);
			driver.navigate().refresh();
			try {
				Thread.sleep(10000);
				if(driver.getCurrentUrl().equals(DIG_URL)){
					addHref(driver);
					delVideoJs(driver);
				}else{
					digBeanJs(driver);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*private void logTreasureJs(WebDriver driver) throws Exception {
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		String path = this.getClass().getClassLoader().getResource("templates/logTreasureJs.js").getPath();
		String logTreasureJs = FileUtil.getTemplateContent(path);
		driver_js.executeScript(logTreasureJs);
		
	}*/
	private void digBeanJs(WebDriver driver) throws Exception{
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		String path = this.getClass().getClassLoader().getResource("templates/digBean.js").getPath();
		String digBeanJs = FileUtil.getTemplateContent(path);
		driver_js.executeScript(digBeanJs);	
	}
	public void delVideoJs(WebDriver driver) throws Exception{
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		String path = this.getClass().getClassLoader().getResource("templates/delVideo.js").getPath();
		String digBeanJs = FileUtil.getTemplateContent(path);
		driver_js.executeScript(digBeanJs);	
	}
	/**
	 * 关闭已经结束挖豆的主播房间
	 * @param digDriverContext
	 * @throws Exception
	 */
	public void closeFinishedTab(DiggerDriverContext digDriverContext) throws Exception {
		WebDriver driver = digDriverContext.getDriver();
		String name = digDriverContext.getName();
		List<Map<String, String>> successHarvest = new ArrayList<Map<String,String>>();
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		Map<String,Long> durationMap = digDriverContext.getDurationMap();
		Set<String> handleList = driver.getWindowHandles();
		for(String handle : handleList){
			driver.switchTo().window(handle);
			String curUrl = driver.getCurrentUrl();
			if(curUrl.indexOf(DIG_URL)<0){
				String closeFlgJs = "return closeFlg;";
				long closeFlg =  (long) driver_js.executeScript(closeFlgJs);
				int treasureSize = driver.findElements(By.className("liveRoom_treasureChest")).size();
				String getHarvest = "return harvest;";
				List<Map<String, String>> curHarvest = (List<Map<String, String>>) driver_js.executeScript(getHarvest);
				long durationTime = durationMap.get(curUrl);
				if(durationTime>60000 && (closeFlg==1 || treasureSize==0)){
					String host = driver.findElement(By.className("host-name")).getAttribute("innerText");
					durationMap.remove(driver.getCurrentUrl());
					for(Map<String, String> digResult : curHarvest){
						String digResultStr = digResult.get("digResult");
						logger.info("挖豆结果:" + digResultStr);
						if(digResultStr.indexOf("手气不佳")==-1){
							successHarvest.add(0,digResult);
							//恭喜你获得银豆400!
							//恭喜你获得金豆10000！奖励已存入包裹中，快去查看吧~
							int num=0;
							try{
								num = Integer.parseInt(digResultStr.substring(7, digResultStr.length()-1));
							}catch(Exception e){
								logger.info("异常挖豆结果:" + digResultStr);
							}
							if(digResultStr.indexOf("银豆")>=0){
								digDriverContext.setSilverBean(digDriverContext.getSilverBean()+num);;
							}else{
								digDriverContext.setGoldBean(digDriverContext.getGoldBean()+num);
							}
						}
					}
					digDriverContext.setDigTimes(digDriverContext.getDigTimes()+curHarvest.size());
					digDriverContext.getSuccessHarvest().addAll(successHarvest);
					logger.info(host + "的直播间挖豆已经结束");
					logger.info(name+" 此页面挖豆次数 : " + curHarvest.size());
					logger.info(name+" 总挖豆次数 : " + digDriverContext.getDigTimes());
					logger.info(name+" 此页面挖豆结果 : " + curHarvest);
					logger.info(name+" 成功挖豆次数 : " + digDriverContext.getSuccessHarvest().size());
					logger.info(name+" 成功挖豆的结果 : " + digDriverContext.getSuccessHarvest());
					logger.info(name+" 总挖豆数 银: " + digDriverContext.getSilverBean());
					logger.info(name+" 总挖豆数 金: " + digDriverContext.getGoldBean());
					driver.close();
					logger.info(name+" 关闭页面 host : " + host);
					logger.info(name+" treasureHosts数目"+treasureHosts.size()+"，treasureHosts列表:"+treasureHosts);
				}
			}
		}
		driver.switchTo().window(digDriverContext.getHandles().get(DIG_URL));
		
	}

	public void remove(DiggerDriverContext digDriverContext) throws Exception {
		//HuyaElfConst.treasureUrls = new HashSet<String>();//记录所有url，暂时用不到，后面如果多账户的话可以使用

		List<Map<String, String>> successHarvest = new ArrayList<Map<String,String>>();
		WebDriver driver = digDriverContext.getDriver();
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		Map<String,String> handles = digDriverContext.getHandles();
		Set<String> handleList = driver.getWindowHandles();
		for(String handle : handleList){
			if(!handle.equals(handles.get(MAIN_URL))){
				driver.switchTo().window(handle);
				String host = driver.findElement(By.className("host-name")).getAttribute("innerText");
				//挖豆
				digBeanJs(driver);
				String closeFlgJs = "return closeFlg;";
				long closeFlg =  (long) driver_js.executeScript(closeFlgJs);
				int treasureSize = driver.findElements(By.className("liveRoom_treasureChest")).size();
				String getHarvest = "return harvest;";
				List<Map<String, String>> curHarvest = (List<Map<String, String>>) driver_js.executeScript(getHarvest);
				if(closeFlg==1 || treasureSize==0){
					for(String hostByshort : treasureHosts){
						if(host.indexOf(hostByshort)>=0 || hostByshort.indexOf(host)>=0){
							treasureHosts.remove(hostByshort);
							treasureUrls.remove(driver.getCurrentUrl());
							handles.remove(hostByshort);
							if(curHarvest.size()==0){
								logger.info("挖豆结果不应该是0");
							}
							for(Map<String, String> digResult : curHarvest){
								String digResultStr = digResult.get("digResult");
								logger.info("挖豆结果:" + digResultStr);
								if(digResultStr.indexOf("手气不佳")==-1){
									successHarvest.add(0,digResult);
									int num = Integer.parseInt(digResultStr.substring(7, digResultStr.length()-1));
									if(digResultStr.indexOf("银豆")>=0){
										digDriverContext.setSilverBean(digDriverContext.getSilverBean()+num);;
									}else{
										digDriverContext.setGoldBean(digDriverContext.getGoldBean()+num);
									}
								}
							}
							digDriverContext.setDigTimes(digDriverContext.getDigTimes()+curHarvest.size());
							digDriverContext.getSuccessHarvest().addAll(successHarvest);
							logger.info(host + "的直播间挖豆已经结束");
							logger.info("此页面挖豆次数 : " + curHarvest.size());
							logger.info("总挖豆次数 : " + digDriverContext.getDigTimes());
							logger.info("------此页面挖豆结果 : " + curHarvest);
							logger.info("成功挖豆次数 : " + digDriverContext.getSuccessHarvest().size());
							logger.info("成功挖豆的结果 : " + digDriverContext.getSuccessHarvest());
							logger.info("总挖豆数 银: " + digDriverContext.getSilverBean());
							logger.info("总挖豆数 金: " + digDriverContext.getGoldBean());
							driver.close();
							logger.info("关闭页面 host : " + host);
							logger.info("treasureHosts数目"+treasureHosts.size()+"，treasureHosts列表:"+treasureHosts);
							break;
						}
					}
				}else{
					logger.info(host + "的直播间正在挖豆");
					//logger.info("还有" +treasureSize +"个宝藏待挖");
					//logger.info("当前挖豆结果 : " + curHarvest);
				}
				logger.info("-------单人分割线--------");
			}
		}
		logger.info("***********************单次分割线**************************");
		driver.switchTo().window(handles.get(MAIN_URL));
	}
	public void digBean(DiggerDriverContext digDriverContext) throws Exception {
		open(digDriverContext);
		dig(digDriverContext);
	}

	private void open(DiggerDriverContext digDriverContext) throws InterruptedException {
		WebDriver driver = digDriverContext.getDriver();
		//driver.switchTo().window(nameOrHandle)
		Map<String,Long> durationMap = digDriverContext.getDurationMap();
		for(String url : treasureUrls){
			if(!durationMap.containsKey(url)){
				while(SystemUsageUtil.getCpuUsage()>0.9){
					Thread.sleep(1000);
				}
				setUrlJs(driver,url);
				clickUrlJs(driver);
				durationMap.put(url, System.currentTimeMillis());
				Thread.sleep(3000);
			}
		}
	}
	private void setUrlJs(WebDriver driver, String url) {
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		String setUrlJs = "$('.duya-header-nav').html(\"<a href='' id ='newUrl'  target='_blank'>newUrl</a>\");$('#newUrl').attr('href','" + url +"');";
		driver_js.executeScript(setUrlJs);	
	}
	private void clickUrlJs(WebDriver driver) {
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		String setUrlJs = "document.getElementById('newUrl').click();";
		driver_js.executeScript(setUrlJs);	
	}

	public void addHref(WebDriver driver) throws Exception {
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		String path = this.getClass().getClassLoader().getResource("templates/addHrefJs.js").getPath();
		String addHrefJs = FileUtil.getTemplateContent(path);
		driver_js.executeScript(addHrefJs);	
	}

	private void dig(DiggerDriverContext digDriverContext) throws Exception {
		WebDriver driver = digDriverContext.getDriver();
		Set<String> handleList = driver.getWindowHandles();
		for(String handle : handleList){
			driver.switchTo().window(handle);
			digDriverContext.getHandles().put(driver.getCurrentUrl(), driver.getWindowHandle());
			if(!driver.getCurrentUrl().equals(DIG_URL)){
				//挖豆
				digBeanJs(driver);
			}
		}
		driver.switchTo().window(digDriverContext.getHandles().get(DIG_URL));
	}


	public void digOpen(WebDriver driver,Map<String,String> handles) throws Exception {
		driver.get("https://www.huya.com/bdcmovie");
		handles.put("https://www.huya.com/bdcmovie", driver.getWindowHandle());
	}
}
