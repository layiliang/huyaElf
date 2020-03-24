package com.lyl.webElf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.webElf.services.DigBeanService;
import com.lyl.webElf.services.HuyaManageService;
import com.lyl.webElf.utils.DriverUtil;

@Controller
@RequestMapping("digBean")
public class DigBeanController {
	@Autowired
	DigBeanService digBeanService;
	@Autowired
	HuyaManageService huyaManageService;

	@RequestMapping("login")
	@ResponseBody
	public void login() throws Exception {
		huyaManageService.login();
	}

	@RequestMapping("open")
	@ResponseBody
	public void open() throws Exception {
		DriverUtil.open("https://www.huya.com/11342412");
	}
	@RequestMapping("digBean")
	@ResponseBody
	public void guess() throws Exception {
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					digBeanService.open();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
		/*new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					digBeanService.digBean();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();*/
	}


}
