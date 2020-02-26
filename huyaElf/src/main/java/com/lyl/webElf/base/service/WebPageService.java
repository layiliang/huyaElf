package com.lyl.webElf.base.service;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import com.lyl.webElf.base.domain.WebPage;
import com.lyl.webElf.utils.ChromeHeadLessDriverCreater;
import com.lyl.webElf.utils.DriverUtil;

@Service
public abstract class WebPageService<T extends WebPage> {
	protected Map<String, String> handles;
	protected Map<String,WebDriver> drivers;
	protected WebDriver driver;
	protected T webPage;

	public Map<String, WebDriver> getDrivers() {
		return drivers;
	}

	public void setDrivers(Map<String, WebDriver> drivers) {
		this.drivers = drivers;
	}

	public WebPageService(){
		//this.driver = DriverUtil.getDriver();
		drivers = new HashMap<String,WebDriver>();
		drivers.put("defaultDriver", new ChromeHeadLessDriverCreater().createDriver());
		driver = drivers.get("defaultDriver");
		this.handles = DriverUtil.getHandles();
	}
	
	public void setWebPageCommon(){
		webPage.setHandle(driver.getWindowHandle());
		webPage.setTitle(driver.getTitle());
		webPage.setUrl(driver.getCurrentUrl());
		webPage.setPageSource(driver.getPageSource());
	}

	public void test(){
		System.out.println(webPage.getUrl());
		System.out.println(webPage.getHandle());
		System.out.println(webPage.getTitle());
	}
	
	public T getWebPage() {
		return webPage;
	}

	public void setWebPage(T webPage) {
		this.webPage = webPage;
	}

	public Map<String, String> getHandles() {
		return handles;
	}

	public void setHandles(Map<String, String> handles) {
		this.handles = handles;
	}
	
}
