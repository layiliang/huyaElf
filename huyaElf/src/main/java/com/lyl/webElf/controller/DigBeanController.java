package com.lyl.webElf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.webElf.services.DigBeanService;

@Controller
public class DigBeanController {
	@Autowired
	DigBeanService digBeanService;

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
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					digBeanService.close();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
	}


}
