package com.lyl.webElf.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lyl.webElf.base.context.ChromeHeadLessDriverContext;
import com.lyl.webElf.base.context.DriverContext;
import com.lyl.webElf.base.service.WebPageService;
import com.lyl.webElf.consts.PageNameConsts;
import com.lyl.webElf.dao.LiveItemMapper;
import com.lyl.webElf.domain.LiveItem;
import com.lyl.webElf.domain.LivePage;
import com.lyl.webElf.domain.PageCommonElement;
import com.lyl.webElf.utils.DriverUtil;
import com.lyl.webElf.utils.JsUtils;

@Service
public class LivePageService extends WebPageService<LivePage> {
	public LivePageService() {
		webPage = new LivePage();
	}

	public void initLivePage(boolean isLogined, int pageNo) {
		initLivePage(isLogined, pageNo, defaultDriverContext);
	}

	public void initLivePage(boolean isLogined, int pageNo, DriverContext driverContext) {
		WebDriver driver = driverContext.getDriver();
		Map<String, String> handles = driverContext.getHandles();
		System.out.println(Thread.currentThread().getName());
		handles.put(PageNameConsts.LIVE_PAGE, driver.getWindowHandle());
		driver.switchTo().window(handles.get(PageNameConsts.LIVE_PAGE));
		if(pageNo != 1){
			String jsStr = "$($('.laypage_main a')["+(pageNo-2)+"]).click()";
			JsUtils.execute(driverContext.getDriver(), jsStr);
		}
		PageCommonElement pageCommonElement = new PageCommonElement();
		if (isLogined) {// 登录成功后
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("login-username"), "Beasty"));
			// wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login-username")));
			pageCommonElement.setCurUser(driver.findElement(By.id("login-username")));
			Actions action = new Actions(driver);
			action.moveToElement(pageCommonElement.getCurUser()).build().perform();
			pageCommonElement.setMybean(driver.findElement(By.className("silver-bean")).getText());
			pageCommonElement.setExitBtn(driver.findElement(By.className("btn-exit")));
		} else {// 未登录或者退出后
			WebDriverWait wait = new WebDriverWait(driver, 20);
			System.out.println(driver.getCurrentUrl());
			
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("login-username"), ""));
			pageCommonElement.setLoginBtn(driver.findElement(By.id("nav-login")));
		}
		webPage.setCurPage(driver.findElement(By.className("laypage_curr")).getText());
		webPage.setHandle(driver.getWindowHandle());
		List<LiveItem> lives = getLives(driverContext);
		webPage.setLives(lives);
		webPage.setNextPage(driver.findElement(By.className("laypage_next")));
		if (pageNo != 1) {
			webPage.setPrePage(driver.findElement(By.className("laypage_prev")));
		}
		webPage.setTitle(driver.getTitle());
		webPage.setTotalPage(driver.findElement(By.className("laypage_last")).getText());
		webPage.setUrl(driver.getCurrentUrl());
		webPage.setPageCommonElement(pageCommonElement);
		System.out.println("initwebPage" + Thread.currentThread().getName() + " " + webPage);

	}

	private List<LiveItem> getLives(DriverContext driverContext) {
		WebDriver driver = driverContext.getDriver();
		Map<String, String> handles = driverContext.getHandles();
		driver.switchTo().window(handles.get(PageNameConsts.LIVE_PAGE));
		List<LiveItem> lives = new ArrayList<>();
		List<WebElement> liveList = driver.findElements(By.className("game-live-item"));
		LiveItem liveItem;
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (WebElement liveItemWebElement : liveList) {
			try {
				liveItem = new LiveItem();
				liveItem.setNum(liveItemWebElement.findElement(By.className("js-num")).getText());
				liveItem.setTitle(liveItemWebElement.findElement(By.className("title")).getText());
				String hostName = liveItemWebElement.findElement(By.className("nick")).getText();
				liveItem.setHostname(hostName);
				// liveItem.setType(liveItemWebElement.findElement(By.className("game-type")).getText());
				liveItem.setUrl(liveItemWebElement.findElement(By.className("video-info")).getAttribute("href"));
				lives.add(liveItem);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return lives;
	}

	public void exit() {
		exit(defaultDriverContext);

	}

	public void exit(DriverContext driverContext) {
		WebDriver driver = driverContext.getDriver();
		Map<String, String> handles = driverContext.getHandles();
		driver.switchTo().window(handles.get(PageNameConsts.LIVE_PAGE));
		Actions action = new Actions(driver);
		action.moveToElement(webPage.getPageCommonElement().getCurUser()).build().perform();
		driver.findElement(By.className("btn-exit")).click();
	}


	public List<LiveItem> getLiveItemList(int pageNo,DriverContext driverContext) {
		initLivePage(false, pageNo,driverContext);
		List<LiveItem> liveItemList = webPage.getLives();
		return liveItemList;
	}

	public static void main(String[] args) {
		List<String> resultList = new ArrayList<>();
		resultList.add("ddd");
		resultList.add("bbb");
		System.out.println(resultList);
	}

	public void openLoginWindow() {
		openLoginWindow(defaultDriverContext);
	}

	public void openLoginWindow(DriverContext driverContext) {
		WebDriver driver = driverContext.getDriver();
		Map<String, String> handles = driverContext.getHandles();
		handles.put(PageNameConsts.LIVE_PAGE, driver.getWindowHandle());
		driver.switchTo().window(handles.get(PageNameConsts.LIVE_PAGE));
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

	public void nextPage() {
		webPage.getNextPage().click();
	}
	@Autowired
	private LiveItemMapper lim;
	public void insertTest() {
		// TODO Auto-generated method stub
		int i = 0 ;
		while(true){
			LiveItem li = new LiveItem();
			li.setUrl("ggg");
			li.setId("1");
			lim.insert(li );
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
			if(i==10){
				break;
			}
		}
	}


}
