package com.lyl.webElf.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.lyl.webElf.base.context.DiggerDriverContext;
import com.lyl.webElf.base.context.DriverContext;
import com.lyl.webElf.base.service.WebPageService;
import com.lyl.webElf.consts.HuyaElfConst;
import com.lyl.webElf.consts.PageNameConsts;
import com.lyl.webElf.domain.LiveItem;
import com.lyl.webElf.domain.User;
import com.lyl.webElf.mapper.LiveItemMapper;
import com.lyl.webElf.task.DailyDigAndTask;
import com.lyl.webElf.utils.DriverUtil;
import com.lyl.webElf.utils.JsUtils;
@Scope("prototype")
@Service
public class HuyaManageService extends WebPageService{
	@Autowired
	private LivePageService livePageService;
	@Autowired
	private HostPageService hostPageService;
	@Autowired
	private LoginWindowService loginWindowService;
	@Autowired
	private TestService testService;
	Logger logger = Logger.getLogger(HuyaManageService.class);
	
	private List<LiveItem> allLiveItemList = new ArrayList<LiveItem>();
	private String dailyGuessUrl;
	@Autowired
	private LiveItemMapper liveItemMapper;
	
	public LivePageService getLivePageService() {
		return livePageService;
	}

	public void setLivePageService(LivePageService livePageService) {
		this.livePageService = livePageService;
	}
	
	public HostPageService getHostPageService() {
		return hostPageService;
	}

	public void setHostPageService(HostPageService hostPageService) {
		this.hostPageService = hostPageService;
	}

	public LoginWindowService getLoginWindowService() {
		return loginWindowService;
	}

	public void setLoginWindowService(LoginWindowService loginWindowService) {
		this.loginWindowService = loginWindowService;
	}

	public void getGuessList(int pageNum) throws Exception {
		while(true){
			String groupId = UUID.randomUUID().toString();
			for(int i = 0;i<pageNum;i++){
				boolean isFirstPage;
				if(i == 0){
					DriverUtil.get("https://www.huya.com/l");
					isFirstPage= true;
				}else{
					//checkPrePages(i);
					isFirstPage = false;
					livePageService.nextPage();
				}
				livePageService.initLivePage(false, isFirstPage);
				Thread.sleep(2222);
				List<LiveItem>  liveItemListCurPage = livePageService.getLiveItemList();
				
				
				for (int k = 0; k < liveItemListCurPage.size(); k++) {
					LiveItem liveItem =  liveItemListCurPage.get(k);
					if(!allLiveItemList.contains(liveItem)){
						liveItem.setGroupId(groupId);
						WebElement link = liveItem.getLink();
						link.click();
						DriverUtil.switchToNewWindow();
						System.out.println(DriverUtil.getDefaultDriver().getCurrentUrl());
						System.out.println(k);
						// hanles.put(PageNameConsts.HOST_PAGE,
						// driver.getWindowHandle());
						if (hostPageService.hasGuess()) {
							liveItem.setGuess(true);
							allLiveItemList.add(liveItem);
							liveItemMapper.insert(liveItem);
						}
						DriverUtil.getDefaultDriver().close();
						DriverUtil.getDefaultDriver().switchTo().window(DriverUtil.getDefaultHandles().get(PageNameConsts.LIVE_PAGE));
					}
					System.out.println("竞猜列表长度"+allLiveItemList.size());
					System.out.println(allLiveItemList);
				}
			}
		}
	}


	public void guess(List<String> hostUrls,DriverContext driverContext) throws Exception {
		//DriverUtil.open(hostUrl);

		//hostPageService.openLoginWindow();
		//loginWindowService.loginByAccount("2295451338","huya123");
		//hostPageService.guess(hostUrls,driverContext);
		testService.guess(hostUrls,driverContext);
	}
	
	public void login(User user) throws Exception{
		login(DriverUtil.getDefaultDriver(),user);
	}
	
	public void login(WebDriver driver,User user) throws Exception{
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		Thread.sleep(1000);
		//driver.findElement(By.id("nav-login")).click();
		String clickJs = "$('#nav-login').click()";
		driver_js.executeScript(clickJs);
		Thread.sleep(1000);
		driver.switchTo().frame("UDBSdkLgn_iframe");
		//loginWindowService.loginByAccount(driver,"2295451338", "huya123", "Beasty");//beasty
		loginWindowService.loginByAccount(driver,user);//拉伊亮
		//loginWindowService.loginByAccount(driver,"hy_16226407", "MMxph550701", "一颗小虎牙");//一颗小虎牙
	}
	public void dailyMission(DiggerDriverContext driverContext) throws Exception{
		int huliangNum = openChests(driverContext);
		Thread.sleep(10000);
		for(int i = 0 ; i < huliangNum;i++){
			String url = HuyaElfConst.LIVE_PAGE_FOR_SENDGIFT[i];
			Thread.sleep(1000);
			sendGift(driverContext,"1",url);
		}
		//每天竞猜三次
		int guessNum = 0;
		if(allLiveItemList.size()>0){
			for(LiveItem liveItem:allLiveItemList){
				guessNum = guessNum+dailyGuess(driverContext,liveItem.getUrl());
				if(guessNum>=3){
					break;
				}
			}
		}
		levelUp(driverContext);
	}

	public void levelUp(DiggerDriverContext driverContext) throws Exception {
		WebDriver driver = driverContext.getDriver();
		DriverUtil.open(driverContext,HuyaElfConst.USER_LEVEL_PAGE);
		Thread.sleep(10000);
		DriverUtil.switchToNewWindow(driver, driverContext.getHandles());
		Thread.sleep(1000);
		String path = this.getClass().getClassLoader().getResource("templates/userLevelUp.js").getPath();
		JsUtils.execute(driverContext.getDriver(),path,null);
		Thread.sleep(60000);
		driver.close();
		driver.switchTo().window(driverContext.getHandles().get(HuyaElfConst.MAIN_PAGE_FOR_DIGGER));
	}
	public int dailyGuess(DiggerDriverContext driverContext,String url) throws Exception {
		WebDriver driver = driverContext.getDriver();
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		DriverUtil.open(driverContext,url);
		Thread.sleep(30000);
		DriverUtil.switchToNewWindow(driver, driverContext.getHandles());
		Thread.sleep(1000);
		String path = this.getClass().getClassLoader().getResource("templates/dailyGuess.js").getPath();
		JsUtils.execute(driverContext.getDriver(),path,null);
		Thread.sleep(60000);
		String guessNumJs = "return guessNum;";
		int guessNum = (int) driver_js
				.executeScript(guessNumJs);
		driver.close();
		driver.switchTo().window(driverContext.getHandles().get(HuyaElfConst.MAIN_PAGE_FOR_DIGGER));
		return guessNum;
	}
	
	public int openChests(DiggerDriverContext driverContext) throws Exception{
		WebDriver driver = driverContext.getDriver();
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		DriverUtil.open(driverContext,HuyaElfConst.LIVE_PAGE_FOR_EXAMPLE);
		Thread.sleep(30000);
		DriverUtil.switchToNewWindow(driver, driverContext.getHandles());
		Thread.sleep(1000);
		String path = this.getClass().getClassLoader().getResource("templates/openChest.js").getPath();
		JsUtils.execute(driverContext.getDriver(),path,null);
		Thread.sleep(30000);
		String checkTreasureFromChestJs = "return chestResult;";
		List<Map<String, String>> treasureFromChest = (List<Map<String, String>>) driver_js
				.executeScript(checkTreasureFromChestJs);
		logger.info(treasureFromChest);
		int huliangNum = 0;
		for(Map<String, String> treasure : treasureFromChest){
			if("虎粮".equals(treasure.get("type"))){
				huliangNum = huliangNum+ Integer.parseInt(treasure.get("num"));
			}
		}
		Thread.sleep(5000);
		driver.close();
		driver.switchTo().window(driverContext.getHandles().get(HuyaElfConst.MAIN_PAGE_FOR_DIGGER));
		return huliangNum;
	}
	public void sendGift(DiggerDriverContext driverContext,String giftIndex, String url) throws Exception{
		WebDriver driver = driverContext.getDriver();
		DriverUtil.open(driverContext,url);
		Thread.sleep(30000);
		DriverUtil.switchToNewWindow(driver, driverContext.getHandles());
		Thread.sleep(1000);
		String path = this.getClass().getClassLoader().getResource("templates/sendGift.js").getPath();
		Map<String, String> params = new HashMap<String,String>();
		params.put("GIFT_INDEX", giftIndex);
		JsUtils.execute(driverContext.getDriver(),path,params);
		Thread.sleep(20000);
		driver.close();
		driver.switchTo().window(driverContext.getHandles().get(HuyaElfConst.MAIN_PAGE_FOR_DIGGER));
	}
	public void subscribeHost(DiggerDriverContext driverContext, String url) throws Exception{
		WebDriver driver = driverContext.getDriver();
		DriverUtil.open(driverContext,url);
		Thread.sleep(10000);
		DriverUtil.switchToNewWindow(driver, driverContext.getHandles());
		Thread.sleep(1000);
		String path = this.getClass().getClassLoader().getResource("templates/subscribeHost.js").getPath();
		JsUtils.execute(driverContext.getDriver(),path,null);
		Thread.sleep(1000);
		driver.close();
		driver.switchTo().window(driverContext.getHandles().get(HuyaElfConst.MAIN_PAGE_FOR_DIGGER));
	}
}
