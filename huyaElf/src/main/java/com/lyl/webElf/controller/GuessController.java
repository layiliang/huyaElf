package com.lyl.webElf.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.webElf.base.context.ChromeDriverContext;
import com.lyl.webElf.base.context.DriverContext;
import com.lyl.webElf.dao.LiveItemMapper;
import com.lyl.webElf.domain.LiveItem;
import com.lyl.webElf.domain.LiveItemExample;
import com.lyl.webElf.services.HuyaManageService;

//@Scope("prototype")
@Controller
public class GuessController {
	@Autowired
	private HuyaManageService huyaManageService;
	@Autowired
	private LiveItemMapper liveItemMapper;

	@RequestMapping("getGuessList")
	@ResponseBody
	public void getGuessList() throws Exception {
		System.out.println(Thread.currentThread().getName());
		huyaManageService.getGuessList();
	}

	@RequestMapping("displayGuessList")
	@ResponseBody
	public List<LiveItem> displayGuessList() throws Exception {
		LiveItemExample liveItemExample = new LiveItemExample();

		List<LiveItem> list = liveItemMapper.selectByExample(liveItemExample);
		return list;
		// return list;
	}

	/*
	 * @RequestMapping("guess")
	 * 
	 * @ResponseBody public void guess(String hostUrl) throws Exception {
	 * DriverContext driverContext = new ChromeDriverContext(); List<String>
	 * hostUrls = new ArrayList<String>(); hostUrls.add(hostUrl);
	 * 
	 * huyaManageService.guess(hostUrls,driverContext); }
	 */

	@RequestMapping("recordGuessData")
	@ResponseBody
	public void recordGuessData() throws Exception {
		DriverContext recordGuessDataDriverContext = new ChromeDriverContext();
		LiveItemExample liveItemExample = new LiveItemExample();
		liveItemExample.createCriteria().andEnableEqualTo("1").andIsrecordingguessdataEqualTo("0");
		//构建竞猜数据
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					List<LiveItem> guessLiveItems = liveItemMapper.selectByExample(liveItemExample);
					try {
						huyaManageService.buildGuessDatas(guessLiveItems, recordGuessDataDriverContext);
						Thread.sleep(600000);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();
		
		//获取及记录竞猜数据
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					huyaManageService.getGuessDataAndRebuildGuessDatas(recordGuessDataDriverContext);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}
