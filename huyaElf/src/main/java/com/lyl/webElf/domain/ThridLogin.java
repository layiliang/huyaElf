package com.lyl.webElf.domain;

import org.openqa.selenium.WebElement;

import com.lyl.webElf.base.domain.WebPage;

public class ThridLogin extends WebPage {
	private WebElement loginType;
	private WebPage login;
	public WebElement getLoginType() {
		return loginType;
	}
	public void setLoginType(WebElement loginType) {
		this.loginType = loginType;
	}
	public WebPage getLogin() {
		return login;
	}
	public void setLogin(WebPage login) {
		this.login = login;
	}
	
}
