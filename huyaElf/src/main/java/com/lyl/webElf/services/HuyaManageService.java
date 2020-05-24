package com.lyl.webElf.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.lyl.webElf.base.context.DiggerDriverContext;
import com.lyl.webElf.base.context.DriverContext;
import com.lyl.webElf.base.context.GuessListDriverContext;
import com.lyl.webElf.base.service.WebPageService;
import com.lyl.webElf.consts.HuyaElfConst;
import com.lyl.webElf.consts.PageNameConsts;
import com.lyl.webElf.dao.GuessDataMapper;
import com.lyl.webElf.dao.LiveItemMapper;
import com.lyl.webElf.domain.GuessData;
import com.lyl.webElf.domain.LiveItem;
import com.lyl.webElf.domain.LiveItemExample;
import com.lyl.webElf.domain.User;
import com.lyl.webElf.utils.DriverUtil;
import com.lyl.webElf.utils.FileUtil;
import com.lyl.webElf.utils.JsUtils;

@Scope("prototype")
@Service
@EnableAutoConfiguration
public class HuyaManageService extends WebPageService {
	@Autowired
	private LivePageService livePageService;
	@Autowired
	private HostPageService hostPageService;
	@Autowired
	private LoginWindowService loginWindowService;
	@Autowired
	private TestService testService;
	Logger logger = Logger.getLogger(HuyaManageService.class);

	private List<LiveItem> guessItems = new ArrayList<LiveItem>();
	@Autowired
	private LiveItemMapper liveItemMapper;

	@Autowired
	private GuessDataMapper guessDataMapper;
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

	public List<LiveItem> getGuessItems() {
		return guessItems;
	}

	public void setGuessItems(List<LiveItem> guessItems) {
		this.guessItems = guessItems;
	}

	public void getGuessList() throws Exception {
		//直播主页
		new Thread(new Runnable() {
			@Override
			public void run() {
				GuessListDriverContext getGuessListDriverContext = new GuessListDriverContext();
				getGuessListDriverContext.setPageNo(1);
				//getGuessListDriverContext.getDriver().get("https://www.huya.com/l");
				getGuessListDriverContext.getDriver().get("https://www.huya.com/g/wzry");
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				getGuessList(1, getGuessListDriverContext);
			}
		}).start();
		Thread.sleep(60000);
		//王者主页
		new Thread(new Runnable() {
			@Override
			public void run() {
				GuessListDriverContext getGuessListDriverContext = new GuessListDriverContext();
				getGuessListDriverContext.setPageNo(1);
				getGuessListDriverContext.getDriver().get("https://www.huya.com/l");
				//getGuessListDriverContext.getDriver().get("https://www.huya.com/g/wzry");
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				getGuessList(1, getGuessListDriverContext);
			}
		}).start();
	}
	protected void getGuessList(int pageNo, DriverContext getGuessListDriverContext) {
		while(true){
			List<LiveItem> allItems = livePageService.getLiveItemList(pageNo, getGuessListDriverContext);
			for (int k = 0; k < allItems.size(); k++) {
				LiveItem liveItem = allItems.get(k);
					// liveItem.setGroupId(groupId);
					String url = liveItem.getUrl();
					JsUtils.execute(getGuessListDriverContext.getDriver(), "window.open('"+url+"')");
					DriverUtil.switchToNewWindow(getGuessListDriverContext);
					if (hostPageService.hasGuess(getGuessListDriverContext)) {
						liveItem.setIsguess("1");
						liveItem.setEnable("1");
						liveItem.setPageno(String.valueOf(pageNo));
						System.out.println(liveItem.getUrl());
						if (!guessItems.contains(liveItem)) {
							liveItem.setIsrecordingguessdata("0");
							liveItem.setCreatetime(new Date());
							liveItemMapper.insert(liveItem);
							guessItems.add(liveItem);
						}else{
							liveItem.setUpdatetime(new Date());
							LiveItemExample liveItemExample = new LiveItemExample();
							liveItemExample.createCriteria().andHostnameEqualTo(liveItem.getHostname());
							liveItemMapper.updateByExample(liveItem, liveItemExample);
						}
					}
					getGuessListDriverContext.getDriver().close();
					getGuessListDriverContext.getDriver().switchTo()
							.window(getGuessListDriverContext.getHandles().get(PageNameConsts.LIVE_PAGE));
			}
			getGuessListDriverContext.getDriver().navigate().refresh();
			logger.info("end");
		}
	}

	public void guess(List<LiveItem> guessLiveItems, DriverContext driverContext) throws Exception {
		new Thread(new Runnable(){
			@Override
			public void run() {
				for(LiveItem liveItem : guessLiveItems){
					try {
						liveItem.setIsrecordingguessdata("1");
						LiveItemExample liveItemExample = new LiveItemExample(); 
						liveItemExample.createCriteria().andUrlEqualTo(liveItem.getUrl());
						liveItemMapper.updateByExample(liveItem, liveItemExample);
						buildGuessDatas(liveItem,driverContext);
						Thread.sleep(10000);
						//buildGuessDatas(driver);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		Thread.sleep(10000);
		getGuessDataAndRebuildGuessDatas(driverContext);
	}
	private void buildGuessDatas(LiveItem liveItem,DriverContext driverContext) throws Exception {
		String url = liveItem.getUrl();
		WebDriver driver = driverContext.getDriver();
		Map<String,String> handles = driverContext.getHandles();
		if(handles.size()!=0){
			JsUtils.execute(driverContext.getDriver(), "window.open('"+url+"')");
			DriverUtil.switchToNewWindow(driverContext);
			System.out.println(driver.getCurrentUrl());
		}
		handles.put(url, driver.getWindowHandle());
		Thread.sleep(1000);
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		String path = this.getClass().getClassLoader().getResource("templates/buildGuessDataBySleep.js").getPath();
		String buildGuessDatasJs = FileUtil.getTemplateContent(path);
		driver_js.executeScript(buildGuessDatasJs);
	}
	
	public void getGuessDataAndRebuildGuessDatas(DriverContext driverContext) throws Exception {
		WebDriver driver = driverContext.getDriver();
		Map<String,String> handles = driverContext.getHandles();
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		for (Entry<String, String> handleMap : handles.entrySet()) {
			String url = (String) handleMap.getKey();
			String handle = (String) handleMap.getValue();
			driver.switchTo().window(handle);
			for (int i = 0; i < 2; i++) {
				String getGuessDataJs = "return guessDatas[" + i + "];";
				List<Map<String, String>> guessDatas = (List<Map<String, String>>) driver_js
						.executeScript(getGuessDataJs);
				// JSONObject.fromObject(jsonResult);
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							saveGuessDatas(guessDatas, driver);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
		
	}
	
	private void saveGuessDatas(List<Map<String, String>> guessDatas,  WebDriver driver) throws Exception {

		String guessId = UUID.randomUUID().toString();
		Date createTime = new Date();
		List<GuessData> list = new ArrayList<>();
		for(int k = 0 ; k <guessDatas.size();k++){
			String dataId = UUID.randomUUID().toString();
			Map<String,String> guessDataMap = guessDatas.get(k);
			GuessData guessData = new GuessData();
			guessData.setRecordTime(new Date(Long.parseLong(guessDataMap.get("recordTime").toString())));
			guessData.setCreateTime(createTime);
			guessData.setGuessId(guessId);
			guessData.setId(dataId);
			guessData.setNum1(stringToInt(guessDataMap.get("num1")));
			guessData.setNum2(stringToInt(guessDataMap.get("num2")));
			String rate1 =  guessDataMap.get("rate1");
			if("马上开种".equals(rate1 )){
				guessData.setRate1(0);
			}else{
				guessData.setRate1(Double.parseDouble(rate1.substring(rate1.length()-3, rate1.length())));
			}
			String rate2 =  guessDataMap.get("rate2");
			if("马上开种".equals(rate2 )){
				guessData.setRate2(0);
			}else{
				guessData.setRate2(Double.parseDouble(rate2.substring(rate2.length()-3, rate2.length())));
			}
			guessDataMapper.insert(guessData);
			list.add(guessData);
		}
	}
	

	
	private int stringToInt(String beans) {
		int result = 0;
		if (beans.contains("亿")) {
			String[] strArr = beans.split("亿");
			int result1 = (int) (Double.parseDouble(strArr[0]) * 100000000);
			result = result + result1;
			if(strArr.length>1){
				int result2 = (int) (Double.parseDouble(strArr[1].split("万")[0]) * 10000);
				result = result + result2;
			}
		} else if (beans.contains("万")) {
			result = (int) (Double.parseDouble(beans.split("万")[0]) * 10000);
		} else {
			result = Integer.parseInt(beans);
		}
		return result;
	}
	public void login(User user) throws Exception {
		login(DriverUtil.getDefaultDriver(), user);
	}

	public void login(WebDriver driver, User user) throws Exception {
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		Thread.sleep(1000);
		// driver.findElement(By.id("nav-login")).click();
		String clickJs = "$('#nav-login').click()";
		driver_js.executeScript(clickJs);
		Thread.sleep(1000);
		driver.switchTo().frame("UDBSdkLgn_iframe");
		// loginWindowService.loginByAccount(driver,"2295451338", "huya123",
		// "Beasty");//beasty
		loginWindowService.loginByAccount(driver, user);// 拉伊亮
		// loginWindowService.loginByAccount(driver,"hy_16226407",
		// "MMxph550701", "一颗小虎牙");//一颗小虎牙
	}

	public void dailyMission(DiggerDriverContext driverContext) throws Exception {
		int huliangNum = openChests(driverContext);
		Thread.sleep(10000);
		for (int i = 0; i < huliangNum; i++) {
			String url = HuyaElfConst.LIVE_PAGE_FOR_SENDGIFT[i];
			Thread.sleep(1000);
			sendGift(driverContext, "1", url);
		}
		// 每天竞猜三次
		int guessNum = 0;
		if (guessItems.size() > 0) {
			for (LiveItem liveItem : guessItems) {
				guessNum = guessNum + dailyGuess(driverContext, liveItem.getUrl());
				if (guessNum >= 3) {
					break;
				}
			}
		}
		levelUp(driverContext);
	}

	public void levelUp(DiggerDriverContext driverContext) throws Exception {
		WebDriver driver = driverContext.getDriver();
		DriverUtil.open(driverContext, HuyaElfConst.USER_LEVEL_PAGE);
		Thread.sleep(10000);
		DriverUtil.switchToNewWindow(driverContext);
		Thread.sleep(1000);
		String path = this.getClass().getClassLoader().getResource("templates/userLevelUp.js").getPath();
		JsUtils.execute(driverContext.getDriver(), path, null);
		Thread.sleep(60000);
		driver.close();
		driver.switchTo().window(driverContext.getHandles().get(HuyaElfConst.MAIN_PAGE_FOR_DIGGER));
	}

	public int dailyGuess(DiggerDriverContext driverContext, String url) throws Exception {
		WebDriver driver = driverContext.getDriver();
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		DriverUtil.open(driverContext, url);
		Thread.sleep(30000);
		DriverUtil.switchToNewWindow(driverContext);
		Thread.sleep(1000);
		String path = this.getClass().getClassLoader().getResource("templates/dailyGuess.js").getPath();
		JsUtils.execute(driverContext.getDriver(), path, null);
		Thread.sleep(60000);
		String guessNumJs = "return guessNum;";
		int guessNum = (int) driver_js.executeScript(guessNumJs);
		driver.close();
		driver.switchTo().window(driverContext.getHandles().get(HuyaElfConst.MAIN_PAGE_FOR_DIGGER));
		return guessNum;
	}

	public int openChests(DiggerDriverContext driverContext) throws Exception {
		WebDriver driver = driverContext.getDriver();
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		DriverUtil.open(driverContext, HuyaElfConst.LIVE_PAGE_FOR_EXAMPLE);
		Thread.sleep(30000);
		DriverUtil.switchToNewWindow(driverContext);
		Thread.sleep(1000);
		String path = this.getClass().getClassLoader().getResource("templates/openChest.js").getPath();
		JsUtils.execute(driverContext.getDriver(), path, null);
		Thread.sleep(30000);
		String checkTreasureFromChestJs = "return chestResult;";
		List<Map<String, String>> treasureFromChest = (List<Map<String, String>>) driver_js
				.executeScript(checkTreasureFromChestJs);
		logger.info(treasureFromChest);
		int huliangNum = 0;
		for (Map<String, String> treasure : treasureFromChest) {
			if ("虎粮".equals(treasure.get("type"))) {
				huliangNum = huliangNum + Integer.parseInt(treasure.get("num"));
			}
		}
		Thread.sleep(5000);
		driver.close();
		driver.switchTo().window(driverContext.getHandles().get(HuyaElfConst.MAIN_PAGE_FOR_DIGGER));
		return huliangNum;
	}

	public void sendGift(DiggerDriverContext driverContext, String giftIndex, String url) throws Exception {
		WebDriver driver = driverContext.getDriver();
		DriverUtil.open(driverContext, url);
		Thread.sleep(30000);
		DriverUtil.switchToNewWindow(driverContext);
		Thread.sleep(1000);
		String path = this.getClass().getClassLoader().getResource("templates/sendGift.js").getPath();
		Map<String, String> params = new HashMap<String, String>();
		params.put("GIFT_INDEX", giftIndex);
		JsUtils.execute(driverContext.getDriver(), path, params);
		Thread.sleep(20000);
		driver.close();
		driver.switchTo().window(driverContext.getHandles().get(HuyaElfConst.MAIN_PAGE_FOR_DIGGER));
	}

	public void subscribeHost(DiggerDriverContext driverContext, String url) throws Exception {
		WebDriver driver = driverContext.getDriver();
		DriverUtil.open(driverContext, url);
		Thread.sleep(10000);
		DriverUtil.switchToNewWindow(driverContext);
		Thread.sleep(1000);
		String path = this.getClass().getClassLoader().getResource("templates/subscribeHost.js").getPath();
		JsUtils.execute(driverContext.getDriver(), path, null);
		Thread.sleep(1000);
		driver.close();
		driver.switchTo().window(driverContext.getHandles().get(HuyaElfConst.MAIN_PAGE_FOR_DIGGER));
	}
}
