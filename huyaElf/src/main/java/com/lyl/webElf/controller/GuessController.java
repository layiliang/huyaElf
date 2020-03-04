package com.lyl.webElf.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.webElf.base.context.ChromeDriverContext;
import com.lyl.webElf.base.context.DriverContext;
import com.lyl.webElf.domain.GuessItem;
import com.lyl.webElf.mapper.GuessDataMapper;
import com.lyl.webElf.services.HostPageService;
import com.lyl.webElf.services.HuyaManageService;
import com.lyl.webElf.services.LivePageService;
import com.lyl.webElf.services.LoginWindowService;
import com.lyl.webElf.utils.ApplicationContextRegister;
import com.lyl.webElf.utils.DriverUtil;

//@Scope("prototype")
@Controller
public class GuessController {
	private static ThreadLocal<HuyaManageService> threadLocal = new ThreadLocal<HuyaManageService>();
	@Autowired
	private HuyaManageService huyaManageService;
	@RequestMapping("getGuessList")
	@ResponseBody
	public List<GuessItem> getGuessList() throws Exception {
		int startPage = 1;
		System.out.println(Thread.currentThread().getName());
		return  huyaManageService.getGuessList(startPage, 1);
	}

	@RequestMapping("guess")
	@ResponseBody
	public void guess(String hostUrl) throws Exception {
		DriverContext driverContext = new ChromeDriverContext();
		List<String> hostUrls = new ArrayList<String>();
		//hostUrls.add(hostUrl);
		hostUrls.add("https://www.huya.com/818929");
		hostUrls.add("https://www.huya.com/678398");
		huyaManageService.guess(hostUrls,driverContext);
	}

	private HuyaManageService getGuessListServiceLocal() {
		
		HuyaManageService huyaManageService = new HuyaManageService();
		LivePageService livePageService = new LivePageService();
		LoginWindowService loginWindowService = new LoginWindowService();
		HostPageService hostPageService = new HostPageService();
		//hostPageService.setGuessDataMapper((GuessDataMapper)(ApplicationContextRegister.getApplicationContext().getBean("guessDataMapper")));
		huyaManageService.setLivePageService(livePageService);
		huyaManageService.setHostPageService(hostPageService);
		huyaManageService.setLoginWindowService(loginWindowService);
		threadLocal.set(huyaManageService);
		HuyaManageService guessListServiceLocal = threadLocal.get();
		return guessListServiceLocal;
	}

}
