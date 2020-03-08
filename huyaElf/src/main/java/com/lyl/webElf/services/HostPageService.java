package com.lyl.webElf.services;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Date;
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
import com.lyl.webElf.domain.GuessResult;
import com.lyl.webElf.domain.HostPage;
import com.lyl.webElf.domain.PageCommonElement;
import com.lyl.webElf.domain.PlayerPanelGuessOpen;
import com.lyl.webElf.mapper.GuessDataMapper;
import com.lyl.webElf.mapper.GuessResultMapper;
import com.lyl.webElf.utils.DriverUtil;
import com.lyl.webElf.utils.FileUtil;

@Service
public class HostPageService extends WebPageService<HostPage> {
	@Autowired
	private GuessDataDao guessDataDao;
	@Autowired
	private GuessDataMapper guessDataMapper; 
	@Autowired
	private GuessResultMapper guessResultMapper; 
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
	public void buildGuessDatas(WebDriver driver) throws Exception{
		logger.info("tttta");
		//driver.get(url);
		Thread.sleep(1000);
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		String path = this.getClass().getClassLoader().getResource("templates/buildGuessData.js").getPath();
		String buildGuessDatasJs = FileUtil.getTemplateContent(path);
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
						List<Map<String,String>> guessDatas = (List<Map<String,String>>)driver_js.executeScript(getGuessDataJs);
						//JSONObject.fromObject(jsonResult);
						String getResultJs = "return $('#guessResult"+index+"').text();";
						String result = (String)driver_js.executeScript(getResultJs);
						new Thread(new Runnable(){

							@Override
							public void run() {
								try {
									saveGuessDatas(guessDatas,result,driver);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}	
						}).start();
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
	
	private void saveGuessDatas(List<Map<String, String>> guessDatas, String result, WebDriver driver) throws Exception {

		String guessId = UUID.randomUUID().toString();
		String[] resultArr = result.split(",");
		GuessResult guessResult = new GuessResult();
		Date createTime = new Date();
		guessResult.setCreateTime(createTime);
		guessResult.setStartTime(new Date(Long.parseLong(resultArr[0])));
		guessResult.setEndTime(new Date(Long.parseLong(resultArr[1])));
		guessResult.setHostName(driver.findElement(By.className("host-name")).getText());
		guessResult.setId(guessId);
		guessResult.setName1(resultArr[3]);
		guessResult.setName2(resultArr[4]);
		guessResult.setResult(resultArr[2]);
		guessResult.setTitle(resultArr[5]);
		guessResult.setUrl(driver.getCurrentUrl());
		guessResultMapper.insert(guessResult);
		List<GuessData> list = new ArrayList<>();
		for(int k = 0 ; k <guessDatas.size();k++){
			String dataId = UUID.randomUUID().toString();
			Map<String,String> guessDataMap = guessDatas.get(k);
			GuessData guessData = new GuessData();
			guessData.setRecordTime(new Date(Long.parseLong(guessDataMap.get("recordTime"))));
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
		/*for(int k = 0 ; k <list.size();k= k+100){
			List<GuessData> guessDatasInsert = new ArrayList<>();
			for(int j = k ; j <k+100 && j < list.size();j++ ){
				guessDatasInsert.add(list.get(j));
			}
			guessDataMapper.insertAll(guessDatasInsert);
		}*/
	}
	

	private void bet() {
		// TODO Auto-generated method stub

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
