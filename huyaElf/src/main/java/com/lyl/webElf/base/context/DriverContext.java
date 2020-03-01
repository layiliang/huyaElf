package com.lyl.webElf.base.context;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

public abstract class  DriverContext {
	protected WebDriver driver;
	protected Map<String,String> handles = new HashMap<String,String>();
	
	public DriverContext() {
		super();
	}
	public DriverContext(WebDriver driver, Map<String, String> handles) {
		super();
		this.driver = driver;
		this.handles = handles;
	}
	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	public Map<String, String> getHandles() {
		return handles;
	}
	public void setHandles(Map<String, String> handles) {
		this.handles = handles;
	}
	
}
