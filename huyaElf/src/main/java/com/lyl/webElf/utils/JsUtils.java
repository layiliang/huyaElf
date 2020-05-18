package com.lyl.webElf.utils;

import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class JsUtils {

	public static void execute(WebDriver driver, String jsStr) {
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		driver_js.executeScript(jsStr);	
	}

	public static void execute(WebDriver driver, String filePath,Map<String,String> params) throws Exception {
	String jsStr = FileUtil.getTemplateContent(filePath);
	if(params!=null){
		for(Entry<String,String> param : params.entrySet()){
			jsStr = jsStr.replace(param.getKey(), param.getValue());
		}
	}
	execute(driver,jsStr);	
	}
}
