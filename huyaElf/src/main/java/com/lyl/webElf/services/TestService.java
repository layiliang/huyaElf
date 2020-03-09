package com.lyl.webElf.services;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyl.webElf.base.context.DriverContext;
import com.lyl.webElf.base.service.WebPageService;
import com.lyl.webElf.dao.GuessDataDao;
import com.lyl.webElf.domain.GuessData;
import com.lyl.webElf.domain.HostPage;
import com.lyl.webElf.mapper.GuessDataMapper;
import com.lyl.webElf.mapper.GuessResultMapper;
import com.lyl.webElf.utils.DriverUtil;
import com.lyl.webElf.utils.FileUtil;

@Service
public class TestService extends WebPageService<HostPage> {
	@Autowired
	private GuessDataDao guessDataDao;
	@Autowired
	private GuessDataMapper guessDataMapper; 
	@Autowired
	private GuessResultMapper guessResultMapper; 
	Logger logger = Logger.getLogger(TestService.class);

	public TestService() {
		webPage = new HostPage();
	}


	public void guess(List<String> urls,DriverContext driverContext) throws Exception {
		WebDriver driver = driverContext.getDriver();
		Map<String,String> handles = driverContext.getHandles();
		/*new Thread(new Runnable(){
			@Override
			public void run() {*/
				for(String url : urls){
					open(url,driverContext);
					try {
						Thread.sleep(10000);
						buildGuessDatas(driver);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			/*}
			
		}).start();*/
		Thread.sleep(10000);
		getGuessDataAndRebuildGuessDatas(driverContext);
	}
	
	private void open(String url,DriverContext driverContext) throws AWTException {
		WebDriver driver = driverContext.getDriver();
		Map<String,String> handles = driverContext.getHandles();
		// TODO Auto-generated method stub
		if(handles.size()!=0){
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.className("toAnchor")));
			DriverUtil.switchToNewWindow(driver, handles);
			System.out.println(driver.getCurrentUrl());
		}
		driver.get(url);
		handles.put(url, driver.getWindowHandle());
	}
	public void buildGuessDatas(WebDriver driver) throws Exception{
		logger.info("tttta");
		//driver.get(url);
		Thread.sleep(1000);
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		String path = this.getClass().getClassLoader().getResource("templates/buildGuessData.js").getPath();
		String buildGuessDatasJs = FileUtil.getTemplateContent(path);
		driver_js.executeScript(buildGuessDatasJs);
	}
	
	public void getGuessDataAndRebuildGuessDatas(DriverContext driverContext) throws Exception {
		WebDriver driver = driverContext.getDriver();
		Map<String,String> handles = driverContext.getHandles();
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		for (Entry<String, String> handleMap : handles.entrySet()) {
			String url = (String) handleMap.getKey();
			String handle = (String) handleMap.getValue();
			driver.switchTo().window(handle);
			for (int i = 0; i < 2; i++) {
				String getGuessDataJs = "return guessDatas[" + i + "];";
				List<Map<String, String>> guessDatas = (List<Map<String, String>>) driver_js
						.executeScript(getGuessDataJs);
				// JSONObject.fromObject(jsonResult);
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							saveGuessDatas(guessDatas, driver);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
		
	}
	
	private void saveGuessDatas(List<Map<String, String>> guessDatas,  WebDriver driver) throws Exception {

		String guessId = UUID.randomUUID().toString();
		Date createTime = new Date();
		List<GuessData> list = new ArrayList<>();
		for(int k = 0 ; k <guessDatas.size();k++){
			String dataId = UUID.randomUUID().toString();
			Map<String,String> guessDataMap = guessDatas.get(k);
			GuessData guessData = new GuessData();
			guessData.setRecordTime(new Date(Long.parseLong(guessDataMap.get("recordTime").toString())));
			guessData.setCreateTime(createTime);
			guessData.setGuessId(guessId);
			guessData.setId(dataId);
			guessData.setNum1(stringToInt(guessDataMap.get("num1")));
			guessData.setNum2(stringToInt(guessDataMap.get("num2")));
			String rate1 =  guessDataMap.get("rate1");
			if("马上开种".equals(rate1 )){
				guessData.setRate1(0);
			}else{
				guessData.setRate1(Double.parseDouble(rate1.substring(rate1.length()-3, rate1.length())));
			}
			String rate2 =  guessDataMap.get("rate2");
			if("马上开种".equals(rate2 )){
				guessData.setRate2(0);
			}else{
				guessData.setRate2(Double.parseDouble(rate2.substring(rate2.length()-3, rate2.length())));
			}
			guessDataMapper.insert(guessData);
			list.add(guessData);
		}
	}
	

	
	private int stringToInt(String beans) {
		int result = 0;
		if (beans.contains("亿")) {
			String[] strArr = beans.split("亿");
			int result1 = (int) (Double.parseDouble(strArr[0]) * 100000000);
			result = result + result1;
			if(strArr.length>1){
				int result2 = (int) (Double.parseDouble(strArr[1].split("万")[0]) * 10000);
				result = result + result2;
			}
		} else if (beans.contains("万")) {
			result = (int) (Double.parseDouble(beans.split("万")[0]) * 10000);
		} else {
			result = Integer.parseInt(beans);
		}
		return result;
	}

	
}
