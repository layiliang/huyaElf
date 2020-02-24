package com.lyl.webElf.domain;

import org.openqa.selenium.WebElement;

public class PhoneMode {
	//
	//private WebElement loginMode;//id="login-head-nav" li
	private WebElement getPwd;
	private WebElement acount;
	private WebElement pwd;
	public WebElement getGetPwd() {
		return getPwd;
	}
	public void setGetPwd(WebElement getPwd) {
		this.getPwd = getPwd;
	}
	/*public WebElement getLoginMode() {
		return loginMode;
	}
	public void setLoginMode(WebElement loginMode) {
		this.loginMode = loginMode;
	}*/
	public WebElement getAcount() {
		return acount;
	}
	public void setAcount(WebElement acount) {
		this.acount = acount;
	}
	public WebElement getPwd() {
		return pwd;
	}
	public void setPwd(WebElement pwd) {
		this.pwd = pwd;
	}
}
