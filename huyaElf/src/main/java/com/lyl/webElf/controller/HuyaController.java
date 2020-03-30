package com.lyl.webElf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.webElf.services.HostPageService;
import com.lyl.webElf.services.LivePageService;
import com.lyl.webElf.services.LoginWindowService;
import com.lyl.webElf.utils.DriverUtil;

@Scope("prototype")
@Controller
public class HuyaController {
	@Autowired
	private LivePageService livePageService;
	@Autowired
	private HostPageService hostPageService;
	@Autowired
	private LoginWindowService loginWindowService;

	@RequestMapping("huyaElfIndex")
	public String huyaElfIndex(){
		return "huyaElfIndex";
	}
	
	/*@RequestMapping("initDriver")
	@ResponseBody
	public void initDriver(){
		WebDriver driver = driverManageService.getDriver();
		livePageService.setDriver(driver);
		hostPageService.setDriver(driver);
	}*/
	
	@RequestMapping("openHuya")
	@ResponseBody
	public void openHuyaLive(){
		DriverUtil.open("https://www.huya.com/l");
		livePageService.initLivePage(false,true);
	}
	@RequestMapping("login")
	@ResponseBody
	public void login(String id,String pwd,String name,String pageSource) throws Exception{
		if("livePage".equals(pageSource)){
			livePageService.openLoginWindow();
		}else{
			hostPageService.openLoginWindow();
		}
		loginWindowService.loginByAccount(id,pwd,name);
	}
	@RequestMapping("livePageExit")
	@ResponseBody
	public void exit(){
		livePageService.exit();
	}
	/*@RequestMapping("getGuessList")
	@ResponseBody
	public List<GuessItem> getGuessList() throws InterruptedException, ExecutionException{
		
		ExecutorService executorService = Executors.newFixedThreadPool(10); 
		List<GuessItem> guessList = new ArrayList<>();
		List<FutureTask<List<GuessItem>>> getGuessListTasks = new ArrayList<FutureTask<List<GuessItem>>>();
		for(int i = 0 ; i < 2 ; i ++){
			final int startPage = i;
			FutureTask<List<GuessItem>> getGuessListTask = new FutureTask<List<GuessItem>>(new Callable<List<GuessItem>>() {
				@Override
				public List<GuessItem> call() throws Exception {
					//initDriver();
					openHuyaLive();
					login("2295451338","huya123","livePage");
					return new ArrayList<GuessItem>();
					//return livePageService.getGuessList(startPage,10);
				}
			});
			getGuessListTasks.add(getGuessListTask);
			executorService.submit(getGuessListTask);
		}
		for(FutureTask<List<GuessItem>> getGuessListTask : getGuessListTasks){
			guessList.addAll(getGuessListTask.get());
		}
		return guessList;
		 
	}*/
	
}
