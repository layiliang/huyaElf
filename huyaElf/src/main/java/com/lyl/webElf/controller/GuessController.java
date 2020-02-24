package com.lyl.webElf.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.webElf.domain.GuessItem;
import com.lyl.webElf.mapper.GuessDataMapper;
import com.lyl.webElf.services.HostPageService;
import com.lyl.webElf.services.HuyaManageService;
import com.lyl.webElf.services.LivePageService;
import com.lyl.webElf.services.LoginWindowService;
import com.lyl.webElf.utils.ApplicationContextRegister;

@Scope("prototype")
@Controller
public class GuessController {
	private static ThreadLocal<HuyaManageService> threadLocal = new ThreadLocal<HuyaManageService>();

	@RequestMapping("getGuessList")
	@ResponseBody
	public List<GuessItem> getGuessList() throws InterruptedException, ExecutionException {

		ExecutorService executorService = Executors.newFixedThreadPool(2);
		List<GuessItem> guessList = new ArrayList<>();
		List<FutureTask<List<GuessItem>>> getGuessListTasks = new ArrayList<FutureTask<List<GuessItem>>>();
		for (int i = 0; i < 1; i++) {
			final int startPage = i;
			FutureTask<List<GuessItem>> getGuessListTask = new FutureTask<List<GuessItem>>(
					new Callable<List<GuessItem>>() {
						@Override
						public List<GuessItem> call() throws Exception {
							// initDriver();
							HuyaManageService huyaManageService = getGuessListServiceLocal();
							return huyaManageService.getGuessList(startPage, 1);
							/*
							 * openHuyaLive(); login("2295451338","huya123");
							 * return new ArrayList<GuessItem>();
							 */
							// return
							// livePageService.getGuessList(startPage,10);
						}
					});
			getGuessListTasks.add(getGuessListTask);
			executorService.submit(getGuessListTask);
		}
		for (FutureTask<List<GuessItem>> getGuessListTask : getGuessListTasks) {
			guessList.addAll(getGuessListTask.get());
		}
		return guessList;

	}

	@RequestMapping("guess")
	@ResponseBody
	public void guess(String hostUrl) throws InterruptedException {
		//hostUrls = new String[2];
		//hostUrls[0] = "https://www.huya.com/haddis";
		//hostUrls[1] = "https://www.huya.com/housangun";
		List<String> hostUrls = new ArrayList<String>();
		//hostUrls.add("https://www.huya.com/131499");
		hostUrls.add(hostUrl);
		//hostUrls.add("https://www.huya.com/520123");
		
		for (String url : hostUrls) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					HuyaManageService huyaManageService = getGuessListServiceLocal();
					try {
						huyaManageService.guess(url);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
			Thread.sleep(60000);
		}
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
