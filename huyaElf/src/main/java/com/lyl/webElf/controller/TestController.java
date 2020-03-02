package com.lyl.webElf.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.webElf.utils.DriverUtil;

//import net.sf.json.JSONObject;

@Controller
public class TestController {
	Logger logger = Logger.getLogger(TestController.class);
	@SuppressWarnings("unchecked")
	@RequestMapping("testIndex")
	@ResponseBody
	public void testIndex(String url,WebDriver driver) throws InterruptedException{
		logger.info("tttt");
		driver.get(url);
		Thread.sleep(1000);
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		String buildGuessDatasJs = 
		"window.guessDatas=new Array();"+
		"window.index=new Array();"+
		"window.interval=new Array();"+
		"window.guessDataInterval=new Array();"+
		"window.resultFlg1=new Array();"+
		"window.resultFlg2=new Array();"+
		"window.endFlg=new Array();"+
		"window.rate1=new Array();"+
		"window.rate2=new Array();"+
		"window.num1=new Array();"+
		"window.num2=new Array();"+
		"window.name1=new Array();"+
		"window.name2=new Array();"+
		"window.idName=new Array();"+
		"window.result=new Array();"+
		"window.title=new Array();"+
		"window.createtime=new Array();"+
		"window.startedFlg=new Array();"+
		"window.guessMainBoxsSize = $('.guess-main-box').length;"+
		"console.log('size'+guessMainBoxsSize);"+
		"window.buildGuessDatas= function(i){"+
			"guessDatas[i] = new Array();"+
			"guessData = new Array();"+
			"index[i]=0;"+
			"interval[i]=0;"+
			"startedFlg[i]=false;"+
			"guessDataInterval[i]=setInterval(function(){"+
				"interval[i]++;"+
				"resultFlg1[i] = $($('.guess-unit span')[i*2]).attr('class')?true:false;"+
				"resultFlg2[i] = $($('.guess-btn')[i*2]).text()=='流局'?true:false;"+
				"endFlg[i] = $($('.guess-btn')[i*2]).text()=='结束种豆'?true:false;"+
				"console.log($($('.guess-btn')[i*2]).text());"+
				"if((resultFlg1[i] || resultFlg2[i]) && startedFlg[i]){"+
						"if(resultFlg1[i]){"+
							"result[i] = $($('.guess-unit span')[i*2]).attr('class');"+
						"}else{"+
							"result[i] = '流局';"+
						"}"+
						"console.log(result[i]);"+
						"idName[i] = 'guessResult'+i;"+
						"$(\"#msg_send_bt\").append(\"<p class ='guessResult' id ='\"+ idName[i]+\"'>\"+result[i] + \"</p>\");"+
						"clearInterval(guessDataInterval[i]);"+
				"}"+
				"if(!endFlg[i] && interval[i]%20==0 && !(resultFlg1[i] || resultFlg2[i])){"+
					"startedFlg[i]=true;"+
					"createtime[i] = (new Date()).toTimeString();"+
					"rate1[i] = $($('.guess-btn')[i*2]).text();"+
					"rate2[i] = $($('.guess-btn')[i*2+1]).text();"+
					"num1[i] = $($('.process-num')[i*2]).text();"+
					"num2[i] = $($('.process-num')[i*2+1]).text();"+
					"name1[i] = $($('.process-name')[i*2]).text();"+
					"name2[i] = $($('.process-name')[i*2+1]).text();"+
					"result[i] = $($('.guess-unit span')[i*2]).attr('class');"+
					"title[i] = $($('.box-title .name')[i]).text();"+
					"guessData[i] = { 'rate1': rate1[i],'rate2': rate2[i],'num1': num1[i],'num2': num2[i],"+
							"'name1': name1[i],'name2': name2[i],'title': title[i],'result1': result[i],'createtime': createtime[i]};"+
					"guessDatas[i].push(guessData[i]);"+
					"index[i]++"+
				"}"+
			"},50);"+
		"};"+
		"for(let j = 0 ; j < guessMainBoxsSize;j++){"+
		"buildGuessDatas(j);"+
		"}";
		driver_js.executeScript(buildGuessDatasJs);
		while(true){
			Thread.sleep(3000);
			if(driver.findElements(By.className("guessResult")) !=null && driver.findElements(By.className("guessResult")).size()>0){
				List<WebElement> guessResults = driver.findElements(By.className("guessResult"));
				int size = guessResults.size();
				logger.info(size);
				for(int i = 0 ; i < size;i++){
					String idName = guessResults.get(i).getAttribute("id");
					int index = Integer.parseInt(idName.substring(idName.length()-1, idName.length()));
					String getGuessDataJs = "return guessDatas["+index+"];";
					List<String> guessDatas = (List<String>)driver_js.executeScript(getGuessDataJs);
					//JSONObject.fromObject(jsonResult);
					String getResultJs = "return $('#guessResult"+index+"').text();";
					String result = (String)driver_js.executeScript(getResultJs);
					String resetJs = "$('#guessResult"+index+"').remove();";
					driver_js.executeScript(resetJs);
					String buildGuessDatasJs2 = "buildGuessDatas("+index+");";
					driver_js.executeScript(buildGuessDatasJs2);
					logger.info(result);
					logger.info(guessDatas);
					logger.info(guessDatas.size());
				}
			}
		}
	}
}