package com.lyl.webElf.base.service;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import com.lyl.webElf.base.context.ChromeHeadLessDriverContext;
import com.lyl.webElf.base.domain.WebPage;
import com.lyl.webElf.utils.DriverUtil;

@Service
public abstract class WebPageService<T extends WebPage> {
	protected ChromeHeadLessDriverContext defaultDriverContext;
	protected T webPage;

	public ChromeHeadLessDriverContext getDefaultDriverContext() {
		return defaultDriverContext;
	}

	public void setDefaultDriverContext(ChromeHeadLessDriverContext defaultDriverContext) {
		this.defaultDriverContext = defaultDriverContext;
	}

	public WebPageService(){
		this.defaultDriverContext = new ChromeHeadLessDriverContext();
	}
	
	public void setWebPageCommon(){
		WebDriver driver = defaultDriverContext.getDriver();
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
	
}
