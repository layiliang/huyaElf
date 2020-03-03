package com.lyl.webElf.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

import com.lyl.webElf.base.context.ChromeHeadLessDriverContext;
import com.lyl.webElf.base.context.DriverContext;
import com.lyl.webElf.base.service.WebPageService;
import com.lyl.webElf.consts.PageNameConsts;
import com.lyl.webElf.controller.TestController;
import com.lyl.webElf.dao.GuessDataDao;
import com.lyl.webElf.domain.GuessData;
import com.lyl.webElf.domain.GuessMainBox;
import com.lyl.webElf.domain.GuessPlan;
import com.lyl.webElf.domain.HostPage;
import com.lyl.webElf.domain.PageCommonElement;
import com.lyl.webElf.domain.PlayerPanelGuessOpen;

@Service
public class HostPageService extends WebPageService<HostPage> {
	@Autowired
	private GuessDataDao guessDataDao;
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

	public void guess(String url) throws Exception {
		guess(url,defaultDriverContext);
	}

	public void guess(String url,DriverContext driverContext) throws Exception {
		WebDriver driver = driverContext.getDriver();
		new TestController().testIndex(url, driver);
		/*Actions action = new Actions(driver);
		Thread.sleep(2222);
		initHostPage(true);
		Thread.sleep(2222);
		action.moveToElement(webPage.getFullscreenBtn()).click().perform();
		action.moveToElement(webPage.getGiftShowBtn()).click().perform();
		action.moveToElement(webPage.getGuessBox()).click().perform();
		String js = "$('#player-video').empty();setInterval(function(){$('#player-video').empty()},60000);";
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		driver_js.executeScript(js);
		keyM(driverContext);*/
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
