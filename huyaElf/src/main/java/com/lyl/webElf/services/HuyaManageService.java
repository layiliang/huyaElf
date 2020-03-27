package com.lyl.webElf.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.lyl.webElf.base.context.ChromeDriverContext;
import com.lyl.webElf.base.context.DriverContext;
import com.lyl.webElf.base.service.WebPageService;
import com.lyl.webElf.consts.PageNameConsts;
import com.lyl.webElf.dao.LiveItemDao;
import com.lyl.webElf.domain.GuessItem;
import com.lyl.webElf.domain.LiveItem;
import com.lyl.webElf.mapper.LiveItemMapper;
import com.lyl.webElf.utils.ChromeDriverCreater;
import com.lyl.webElf.utils.ChromeHeadLessDriverCreater;
import com.lyl.webElf.utils.DriverUtil;
import com.lyl.webElf.utils.PhantomjsDriverCreater;
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
	
	private List<LiveItem> allLiveItemList = new ArrayList<LiveItem>();
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
					DriverUtil.open("https://www.huya.com/l");
					isFirstPage= true;
				}else{
					checkPrePages(i);
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
						}
						DriverUtil.getDefaultDriver().close();
						DriverUtil.getDefaultDriver().switchTo().window(DriverUtil.getDefaultHandles().get(PageNameConsts.LIVE_PAGE));
						allLiveItemList.add(liveItem);
						liveItemMapper.insert(liveItem);
					}
				}
			}
		}
	}

	private void checkPrePages(int curPageNum) throws Exception {
		for(int i = 0;i<curPageNum;i++){
			boolean isFirstPage;
			if(i == 0){
				DriverUtil.open("https://www.huya.com/l");
				isFirstPage= true;
			}else{
				isFirstPage = false;
				livePageService.nextPage();
			}
			livePageService.initLivePage(false, isFirstPage);
			Thread.sleep(2222);
			List<LiveItem>  liveItemListCurPage = livePageService.getLiveItemList();

			for (int k = 0; k < liveItemListCurPage.size(); k++) {
				LiveItem liveItem =  liveItemListCurPage.get(k);
				if(!allLiveItemList.contains(liveItem)){
					WebElement link = liveItem.getLink();
					link.click();
					DriverUtil.switchToNewWindow();
					System.out.println(DriverUtil.getDefaultDriver().getCurrentUrl());
					System.out.println(k);
					// hanles.put(PageNameConsts.HOST_PAGE,
					// driver.getWindowHandle());
					if (hostPageService.hasGuess()) {
						liveItem.setGuess(true);
					}
					DriverUtil.getDefaultDriver().close();
					DriverUtil.getDefaultDriver().switchTo().window(DriverUtil.getDefaultHandles().get(PageNameConsts.LIVE_PAGE));
					allLiveItemList.add(liveItem);
					liveItemMapper.insert(liveItem);
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
	
	public void login() throws Exception{
		login(DriverUtil.getDefaultDriver());
	}
	
	public void login(WebDriver driver) throws Exception{
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		Thread.sleep(1000);
		//driver.findElement(By.id("nav-login")).click();
		String clickJs = "$('#nav-login').click()";
		driver_js.executeScript(clickJs);
		Thread.sleep(1000);
		driver.switchTo().frame("UDBSdkLgn_iframe");
		//loginWindowService.loginByAccount("2295451338", "huya123");//beasty
		loginWindowService.loginByAccount("2253812186", "huya1234");//拉伊亮
		//loginWindowService.loginByAccount("hy_16226407", "MMxph550701");//一颗小虎牙
	}
	
}
