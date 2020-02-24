package com.lyl.webElf.domain;

import java.util.Map;

import org.openqa.selenium.WebElement;

import com.lyl.webElf.base.domain.WebPage;

public class QqLogin extends WebPage{
	private Map<String,WebElement> validQqs;

	public Map<String, WebElement> getValidQqs() {
		return validQqs;
	}

	public void setValidQqs(Map<String, WebElement> validQqs) {
		this.validQqs = validQqs;
	}
	
	
}
