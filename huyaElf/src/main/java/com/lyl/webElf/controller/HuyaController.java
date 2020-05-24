package com.lyl.webElf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.webElf.domain.User;
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
	@RequestMapping("testA")
	@ResponseBody
	public void testA(){
		
		livePageService.insertTest();
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
		DriverUtil.get("https://www.huya.com/l");
		livePageService.initLivePage(false,1);
	}
	@RequestMapping("login")
	@ResponseBody
	public void login(User user,String pageSource) throws Exception{
		if("livePage".equals(pageSource)){
			livePageService.openLoginWindow();
		}else{
			hostPageService.openLoginWindow();
		}
		loginWindowService.loginByAccount(user);
	}
	@RequestMapping("livePageExit")
	@ResponseBody
	public void exit(){
		livePageService.exit();
	}
	
	
}
