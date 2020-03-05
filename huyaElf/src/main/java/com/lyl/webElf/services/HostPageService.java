package com.lyl.webElf.services;

import java.awt.AWTException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyl.webElf.base.context.DriverContext;
import com.lyl.webElf.base.service.WebPageService;
import com.lyl.webElf.consts.PageNameConsts;
import com.lyl.webElf.dao.GuessDataDao;
import com.lyl.webElf.domain.GuessData;
import com.lyl.webElf.domain.GuessMainBox;
import com.lyl.webElf.domain.GuessPlan;
import com.lyl.webElf.domain.HostPage;
import com.lyl.webElf.domain.PageCommonElement;
import com.lyl.webElf.domain.PlayerPanelGuessOpen;
import com.lyl.webElf.mapper.GuessDataMapper;
import com.lyl.webElf.utils.DriverUtil;

@Service
public class HostPageService extends WebPageService<HostPage> {
	@Autowired
	private GuessDataDao guessDataDao;
	@Autowired
	private GuessDataMapper guessDataMapper; 
	Logger logger = Logger.getLogger(HostPageService.class);

	public HostPageService() {
		webPage = new HostPage();
	}

	public void initHostPage(boolean isLogined) {
		initHostPage(isLogined, defaultDriverContext);
	}

	public void initHostPage(boolean isLogined, DriverContext driverContext) {
		WebDriver driver = driverContext.getDriver();
		Map<String, String> handles = driverContext.getHandles();
		Actions action = new Actions(driver);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		handles.put(PageNameConsts.HOST_PAGE, driver.getWindowHandle());
		driver.switchTo().window(handles.get(PageNameConsts.HOST_PAGE));
		PageCommonElement pageCommonElement = new PageCommonElement();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		if (isLogined) {// 登录成功后
			// wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("login-username"),
			// "Beasty"));
			// wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login-username")));
			pageCommonElement.setCurUser(driver.findElement(By.id("login-username")));
			action.moveToElement(pageCommonElement.getCurUser()).build().perform();
			pageCommonElement.setMybean(driver.findElement(By.id("J_huyaNavUserCardAssetsSb")).getText());
			pageCommonElement.setExitBtn(driver.findElement(By.id("nav-loggout")));
		} else {// 未登录或者退出成功后
			wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("login-username"), ""));
			pageCommonElement.setLoginBtn(driver.findElement(By.id("nav-login")));
		}
		webPage.setHostName(driver.findElement(By.className("host-name")));
		webPage.setMsgInput(driver.findElement(By.id("pub_msg_input")));
		webPage.setMsgSendButton(driver.findElement(By.id("msg_send_bt")));
		// hostPage.setCloseCreateLayer(closeCreateLayer);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("player-fullscreen-btn")));

		webPage.setFullscreenBtn(driver.findElement(By.id("player-fullscreen-btn")));
		webPage.setGifts(driver.findElements(By.className("plaer-face-icon-bg")));
		webPage.setGiftShowBtn(driver.findElement(By.id("gift-show-btn")));
		webPage.setPlayerVideotypeCur(driver.findElement(By.className("player-videotype-cur")));
		webPage.setPlayerVideoTypes(
				driver.findElement(By.className("player-videotype-list")).findElements(By.tagName("li")));
		webPage.setSubscribeBtn(driver.findElement(By.id("activityCount")));
		webPage.setSubscribed(
				driver.findElement(By.className("subscribe-control")).getText().equals("已订阅") ? true : false);
		if (webPage.isSubscribed()) {
			webPage.getSubscribeBtn().click();
			webPage.setConfirmToDisSubscribe(driver.findElement(By.className("subscribe-layer-cancel-control"))
					.findElements(By.tagName("span")).get(0));
			webPage.setCancelToDisSubscribe(driver.findElement(By.className("subscribe-layer-cancel-control"))
					.findElements(By.tagName("span")).get(1));
			webPage.getCancelToDisSubscribe().click();
		}

		webPage.setHandle(driver.getWindowHandle());
		webPage.setTitle(driver.getTitle());
		webPage.setUrl(driver.getCurrentUrl());
		webPage.setPageCommonElement(pageCommonElement);
		webPage.setPlayerBox(driver.findElement(By.id("player-box")));
		webPage.setPlayerBoxStat3s(driver.findElements(By.className("player-box-stat3")));
		webPage.setPlayerBoxStat4s(driver.findElements(By.className("player-box-stat4")));
		webPage.setPlayerCtrlWrap(driver.findElement(By.id("player-ctrl-wrap")));
		webPage.setPlayerMenuPanel(driver.findElement(By.className("player-menu-panel")));
		if (hasGuess()) {
			webPage.setGuessBox(driver.findElement(By.className("guess-box")));
			List<WebElement> guessMainBoxWebElements = driver.findElements(By.className("guess-main-box"));
			List<GuessMainBox> guessMainBoxs = new ArrayList<>();
			for (WebElement guessMainBoxWebElement : guessMainBoxWebElements) {
				GuessMainBox guessMainBox = new GuessMainBox();
				guessMainBox.setBoxTitle(guessMainBoxWebElement.findElement(By.className("box-title")));
				guessMainBox.setGuessBtn1(guessMainBoxWebElement.findElements(By.className("guess-btn")).get(0));
				guessMainBox.setGuessBtn2(guessMainBoxWebElement.findElements(By.className("guess-btn")).get(1));
				guessMainBox.setGuessName1(guessMainBoxWebElement.findElements(By.className("process-name")).get(0));
				guessMainBox.setGuessName2(guessMainBoxWebElement.findElements(By.className("process-name")).get(1));
				guessMainBox.setGuessNum1(guessMainBoxWebElement.findElements(By.className("process-num")).get(0));
				guessMainBox.setGuessNum2(guessMainBoxWebElement.findElements(By.className("process-num")).get(1));
				guessMainBox.setGuessOpen(guessMainBoxWebElement.findElement(By.className("guess-open")));
				guessMainBoxs.add(guessMainBox);
			}
			webPage.setGuessMainBoxs(guessMainBoxs);
			webPage.setGuessMainClose(driver.findElement(By.className("guess-main-close")));
			GuessPlan guessPlan = new GuessPlan();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("guess-plan")));
			guessPlan.setBeanInput(driver.findElement(By.className("guess-plan")).findElement(By.tagName("input")));
			guessPlan.setBtn(driver.findElement(By.className("guess-plan")).findElement(By.tagName("button")));
			guessPlan.setLeftBean(driver.findElement(By.className("left-bean")));
			guessPlan.setMyBean(driver.findElement(By.className("my-bean")));
			guessPlan.setTitle(driver.findElement(By.className("my-choice")));
			guessPlan.setWinBean(driver.findElement(By.className("win-bean")));
			guessPlan.setClose(driver.findElement(By.className("guess-plan")).findElement(By.className("close")));
			webPage.setGuessPlan(guessPlan);
			/*
			 * if
			 * (driver.findElements(By.className("guess-result-bottom")).size()
			 * != 0) { webPage.setGuessResultBottom(
			 * driver.findElement(By.className("guess-result-bottom")).
			 * findElement(By.tagName("button"))); }
			 */
			if (isLogined) {
				action.moveToElement(webPage.getGuessBox()).click().perform();
				webPage.getGuessMainBoxs().get(0).getGuessOpen().click();
				PlayerPanelGuessOpen playerPanelGuessOpen = new PlayerPanelGuessOpen();
				playerPanelGuessOpen.setGuessAmount(driver.findElements(By.className("guess-open-input")).get(1));
				playerPanelGuessOpen.setGuessOpenConfirm(driver.findElement(By.className("guess-open-confirm")));
				playerPanelGuessOpen.setGuessOpenRadio1(driver.findElements(By.className("guess-open-radio")).get(0));
				playerPanelGuessOpen.setGuessOpenRadio2(driver.findElements(By.className("guess-open-radio")).get(1));
				playerPanelGuessOpen.setGuessRate(driver.findElements(By.className("guess-open-input")).get(0));
				playerPanelGuessOpen.setPlayerPanelClose(driver.findElement(By.id("player-panel-guess-open"))
						.findElement(By.className("player-panel-close")));
				webPage.setPlayerPanelGuessOpen(playerPanelGuessOpen);
				action.moveToElement(playerPanelGuessOpen.getPlayerPanelClose()).click().perform();
				action.moveToElement(webPage.getGuessMainClose()).click().perform();
			}
		}
	}

	public boolean hasGuess() {
		return hasGuess(defaultDriverContext);
	}

	public boolean hasGuess(DriverContext driverContext) {
		WebDriver driver = driverContext.getDriver();
		List<WebElement> guessMainBoxWebElements;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		try {
			// wait.until(ExpectedConditions.presenceOfElementLocated(By.className("gift-box-icon")));
			wait = new WebDriverWait(driver, 5);
			Thread.sleep(5000);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("guess-main-box")));
			guessMainBoxWebElements = driver.findElements(By.className("guess-main-box"));
			System.out.println("guessMainBoxWebElements.size = " + guessMainBoxWebElements.size());
		} catch (Exception e) {
			System.out.println("no");
			return false;
		}
		return guessMainBoxWebElements.size() != 0 ? true : false;
	}

	public void guess(List<String> urls,DriverContext driverContext) throws Exception {
		WebDriver driver = driverContext.getDriver();
		Map<String,String> handles = driverContext.getHandles();
		/*new Thread(new Runnable(){
			@Override
			public void run() {*/
				for(String url : urls){
					open(url,driverContext);
					try {
						Thread.sleep(10000);
						buildGuessDatas(driver);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			/*}
			
		}).start();*/
		
		getGuessDataAndRebuildGuessDatas(driverContext);
	}
	
	private void open(String url,DriverContext driverContext) throws AWTException {
		WebDriver driver = driverContext.getDriver();
		Map<String,String> handles = driverContext.getHandles();
		// TODO Auto-generated method stub
		if(handles.size()!=0){
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.className("toAnchor")));
			DriverUtil.switchToNewWindow(driver, handles);
			System.out.println(driver.getCurrentUrl());
		}
		driver.get(url);
		handles.put(url, driver.getWindowHandle());
	}
	public void buildGuessDatas(WebDriver driver) throws InterruptedException{
		logger.info("tttta");
		//driver.get(url);
		Thread.sleep(1000);
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		String buildGuessDatasJs = 
		"window.guessDatas=new Array();"+
		"window.index=new Array();"+
		"window.interval=new Array();"+
		"window.guessDataInterval=new Array();"+
		"window.resultFlg1=new Array();"+
		"window.resultFlg2=new Array();"+
		"window.endFlg=new Array();"+
		"window.rate1=new Array();"+
		"window.rate2=new Array();"+
		"window.num1=new Array();"+
		"window.num2=new Array();"+
		"window.name1=new Array();"+
		"window.name2=new Array();"+
		"window.idName=new Array();"+
		"window.result=new Array();"+
		"window.title=new Array();"+
		"window.createtime=new Array();"+
		"window.startedFlg=new Array();"+
		"window.guessMainBoxsSize = $('.guess-main-box').length;"+
		"console.log('size'+guessMainBoxsSize);"+
		"window.buildGuessDatas= function(i){"+
			"guessDatas[i] = new Array();"+
			"guessData = new Array();"+
			"index[i]=0;"+
			"interval[i]=0;"+
			"startedFlg[i]=false;"+
			"guessDataInterval[i]=setInterval(function(){"+
				"interval[i]++;"+
				"resultFlg1[i] = $($('.guess-unit span')[i*2]).attr('class')?true:false;"+
				"resultFlg2[i] = $($('.guess-btn')[i*2]).text()=='流局'?true:false;"+
				"endFlg[i] = $($('.guess-btn')[i*2]).text()=='结束种豆'?true:false;"+
				"console.log($($('.guess-btn')[i*2]).text());"+
				"if((resultFlg1[i] || resultFlg2[i]) && startedFlg[i]){"+
						"if(resultFlg1[i]){"+
							"result[i] = $($('.guess-unit span')[i*2]).attr('class');"+
						"}else{"+
							"result[i] = '流局';"+
						"}"+
						"console.log(result[i]);"+
						"idName[i] = 'guessResult'+i;"+
						"$(\"#msg_send_bt\").append(\"<p class ='guessResult' id ='\"+ idName[i]+\"'>\"+result[i] + \"</p>\");"+
						"clearInterval(guessDataInterval[i]);"+
				"}"+
				"if(!endFlg[i] && interval[i]%20==0 && !(resultFlg1[i] || resultFlg2[i])){"+
					"startedFlg[i]=true;"+
					"createtime[i] = (new Date()).toTimeString();"+
					"rate1[i] = $($('.guess-btn')[i*2]).text();"+
					"rate2[i] = $($('.guess-btn')[i*2+1]).text();"+
					"num1[i] = $($('.process-num')[i*2]).text();"+
					"num2[i] = $($('.process-num')[i*2+1]).text();"+
					"name1[i] = $($('.process-name')[i*2]).text();"+
					"name2[i] = $($('.process-name')[i*2+1]).text();"+
					"result[i] = $($('.guess-unit span')[i*2]).attr('class');"+
					"title[i] = $($('.box-title .name')[i]).text();"+
					"guessData[i] = { 'rate1': rate1[i],'rate2': rate2[i],'num1': num1[i],'num2': num2[i],"+
							"'name1': name1[i],'name2': name2[i],'title': title[i],'result1': result[i],'createtime': createtime[i]};"+
					"guessDatas[i].push(guessData[i]);"+
					"index[i]++"+
				"}"+
			"},50);"+
		"};"+
		"for(let j = 0 ; j < guessMainBoxsSize;j++){"+
		"buildGuessDatas(j);"+
		"}";
		driver_js.executeScript(buildGuessDatasJs);
	}
	public void getGuessDataAndRebuildGuessDatas(DriverContext driverContext) throws Exception {
		WebDriver driver = driverContext.getDriver();
		Map<String,String> handles = driverContext.getHandles();
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		while(true){
			Thread.sleep(30000);
			for(Entry<String,String> handleMap : handles.entrySet()){
				String url = (String) handleMap.getKey();
				String handle = (String) handleMap.getValue();
				driver.switchTo().window(handle);
				if(driver.findElements(By.className("guessResult")) !=null && driver.findElements(By.className("guessResult")).size()>0){
					List<WebElement> guessResults = driver.findElements(By.className("guessResult"));
					int size = guessResults.size();
					logger.info(size);
					for(int i = 0 ; i < size;i++){
						String idName = guessResults.get(i).getAttribute("id");
						int index = Integer.parseInt(idName.substring(idName.length()-1, idName.length()));
						String getGuessDataJs = "return guessDatas["+index+"];";
						List<Map<String,Object>> guessDatas = (List<Map<String,Object>>)driver_js.executeScript(getGuessDataJs);
						//JSONObject.fromObject(jsonResult);
						String getResultJs = "return $('#guessResult"+index+"').text();";
						String result = (String)driver_js.executeScript(getResultJs);
						new Thread(new Runnable(){

							@Override
							public void run() {
								try {
									saveGuessDatas(guessDatas,result);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
						});
						String resetJs = "$('#guessResult"+index+"').remove();";
						driver_js.executeScript(resetJs);
						String buildGuessDatasJs2 = "buildGuessDatas("+index+");";
						driver_js.executeScript(buildGuessDatasJs2);
						logger.info(result);
						logger.info(guessDatas);
						logger.info(guessDatas.size());
					}
				}
			}
		}
	}
	
	private void saveGuessDatas(List<Map<String, Object>> guessDatas, String result) throws Exception {
		List<GuessData> list = new ArrayList<>();
		for(int k = 0 ; k <guessDatas.size();k++){
			Map<String,Object> guessDataMap = guessDatas.get(k);
			GuessData guessData = new GuessData();
			guessData = (GuessData) map2Obj(guessDataMap, guessData.getClass());
			list.add(guessData);
			System.out.println(guessData.getBoxTitle());
		}
		guessDataMapper.insertBatch(list);
	}

public static void main(String[] args) {
	List<String> list = new ArrayList<String>();
	list.add("aaa");
	list.add("bbb");
	list.add("c");
	list.add("d");
	System.out.println(list);
}
	public Object map2Obj(Map<String,Object> map,Class<?> clz) throws Exception{
		Object obj = clz.newInstance();
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for(Field field:declaredFields){
			int mod = field.getModifiers();
			if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
				continue;
			}
			field.setAccessible(true);
			field.set(obj, map.get(field.getName()));
		}
		return obj;
	}
	private void keyM(DriverContext driverContext) throws InterruptedException {
		WebDriver driver = driverContext.getDriver();
		List<GuessData> guessDataPerGameList = new ArrayList<GuessData>();
		try {
			List<GuessMainBox> guessMainBoxs = webPage.getGuessMainBoxs();
			String gameId = UUID.randomUUID().toString();
			int index = 0;
			while (true) {
				index++;
				boolean endFlg = false;
				Thread.sleep(100);
				String resultFlg = driver.findElements(By.id("guess-result")).get(0).getAttribute("class");
				System.out.println(resultFlg);
				if (!"".equals(resultFlg) && !endFlg) {
					// TODO
					// 记录胜负信息
					// 将该场游戏的竞猜数据插入数据库
					addGuessData(gameId, guessDataPerGameList, driverContext);
					endFlg = true;
					guessDataDao.insertList(guessDataPerGameList);

				} else if (index % 50 == 0 && !"结束种豆".equals(driver.findElements(By.className("guess-main-box")).get(0)
						.findElement(By.className("guess-btn")).getText())) {
					// TODO
					bet();
					System.out.println("--------------------------------------------");
					addGuessData(gameId, guessDataPerGameList, driverContext);
				}
			}
		} catch (Exception e) {
			logger.info("竞猜信息获取异常", e);
			// guessDataDao.insertList(guessDataPerGameList);
			keyM(driverContext);
		}
	}

	private void addGuessData(String gameId, List<GuessData> guessDataPerGameList, DriverContext driverContext) {
		WebDriver driver = defaultDriverContext.getDriver();
		List<WebElement> guessMainBoxWebElements = driver.findElements(By.className("guess-main-box"));
		for (WebElement guessMainBoxWebElement : guessMainBoxWebElements) {
			GuessData guessData = new GuessData();
			guessData.setGameId(gameId);
			guessData.setId(UUID.randomUUID().toString());
			guessData.setBoxTitle(guessMainBoxWebElement.findElement(By.className("box-title")).getText());
			guessData.setRate1(guessMainBoxWebElement.findElements(By.className("guess-btn")).get(0).getText());
			guessData.setRate2(guessMainBoxWebElement.findElements(By.className("guess-btn")).get(1).getText());
			guessData.setGuessName1(guessMainBoxWebElement.findElements(By.className("process-name")).get(0).getText());
			guessData.setGuessName2(guessMainBoxWebElement.findElements(By.className("process-name")).get(1).getText());
			guessData.setGuessNum1(guessMainBoxWebElement.findElements(By.className("process-num")).get(0).getText());
			guessData.setGuessNum2(guessMainBoxWebElement.findElements(By.className("process-num")).get(1).getText());
			guessData.setHostName(driver.findElement(By.className("host-name")).getText());
			guessData.setGuessResult1(
					guessMainBoxWebElement.findElements(By.id("guess-result")).get(0).getAttribute("class"));
			guessData.setGuessResult2(
					guessMainBoxWebElement.findElements(By.id("guess-result")).get(1).getAttribute("class"));
			guessDataPerGameList.add(guessData);
		}
	}

	private void bet() {
		// TODO Auto-generated method stub

	}

	private int stringToInt(String beans) {
		int result = 0;
		if (beans.contains("亿")) {
			result = (int) (Double.parseDouble(beans.split("亿")[0]) * 100000000);
		} else if (beans.contains("万")) {
			result = (int) (Double.parseDouble(beans.split("万")[0]) * 10000);
		} else {
			result = Integer.parseInt(beans);
		}
		return result;
	}

	public void openLoginWindow() {
		openLoginWindow(defaultDriverContext);
	}

	public void openLoginWindow(DriverContext driverContext) {
		WebDriver driver = driverContext.getDriver();
		Map<String, String> handles = driverContext.getHandles();
		// TODO Auto-generated method stub
		handles.put(PageNameConsts.HOST_PAGE, driver.getWindowHandle());
		driver.switchTo().window(handles.get(PageNameConsts.HOST_PAGE));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.id("nav-login")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.switchTo().frame("UDBSdkLgn_iframe");
	}
}
