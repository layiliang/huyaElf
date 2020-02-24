package com.lyl.webElf.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.lyl.webElf.consts.PageNameConsts;
import com.lyl.webElf.domain.GuessItem;
import com.lyl.webElf.domain.LiveItem;
import com.lyl.webElf.utils.DriverUtil;
@Scope("prototype")
@Service
public class HuyaManageService {
	@Autowired
	private LivePageService livePageService;
	@Autowired
	private HostPageService hostPageService;
	@Autowired
	private LoginWindowService loginWindowService;
	
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

	public List<GuessItem> getGuessList(int startPage,int pageNum) throws Exception {
		DriverUtil.open("https://www.huya.com/g/wzry");
		//livePageService.openLoginWindow();
		//loginWindowService.loginByAccount("2295451338","huya123");
		livePageService.initLivePage(false,true);
		try {
			Thread.sleep(2222);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<LiveItem>  liveItemList = livePageService.getLiveItemList(startPage, pageNum);
		Set<GuessItem> tempList = new HashSet<>();
		for(LiveItem liveItem : liveItemList){
			// for (int k = 0; k < 15; k++) {
			WebElement link = liveItem.getLink();
			link.click();
			DriverUtil.switchToNewWindow();
			// hanles.put(PageNameConsts.HOST_PAGE,
			// driver.getWindowHandle());
			if (hostPageService.hasGuess()) {
				GuessItem guessItem = new GuessItem();
				guessItem.setHostName(liveItem.getHostName());
				guessItem.setUrl(liveItem.getUrl());
				guessItem.setNum(liveItem.getNum());
				guessItem.setOnTv(liveItem.isOnTv());
				guessItem.setTitle(liveItem.getTitle());
				guessItem.setType(liveItem.getType());
				tempList.add(guessItem);
			}
			DriverUtil.getDriver().close();
			DriverUtil.getDriver().switchTo().window(DriverUtil.getHandles().get(PageNameConsts.LIVE_PAGE));
		}
		List<GuessItem> resultList = new ArrayList<>();
		for (GuessItem guessItem : tempList) {
			resultList.add(guessItem);
		}
		resultList.sort(new Comparator<GuessItem>() {

			@Override
			public int compare(GuessItem o1, GuessItem o2) {
				// TODO Auto-generated method stub
				return o1.getNum().compareTo(o2.getNum());
			}
		});
		return resultList;
	}

	public void guess(String hostUrl) throws Exception {
		DriverUtil.open(hostUrl);

		//hostPageService.openLoginWindow();
		//loginWindowService.loginByAccount("2295451338","huya123");
		hostPageService.guess();
	}
	
}
