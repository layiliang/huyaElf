package com.lyl.webElf.base.service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import com.lyl.webElf.base.domain.WebPage;
import com.lyl.webElf.utils.ChromeHeadLessDriverCreater;
import com.lyl.webElf.utils.DriverUtil;
import com.lyl.webElf.utils.PhantomjsDriverCreater;

@Service
public abstract class WebPageService<T extends WebPage> {
	protected Map<String, String> handles;
	protected WebDriver driver;
	protected T webPage;

	public WebPageService(){
		this.driver = DriverUtil.initDriver(new ChromeHeadLessDriverCreater());
		//this.driver = DriverUtil.getDriver();
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

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	// TODO
	/**
	 * 转到新打开的窗口，应再加些判断
	 */
	public void switchToNewWindow() {
		Set<String> newHandles = driver.getWindowHandles();
		Collection<String> oldHandles = handles.values();
		for (String handle : newHandles) {
			if (!oldHandles.contains(handle)) {
				driver.switchTo().window(handle);
				break;
			}
		}
	}

}
