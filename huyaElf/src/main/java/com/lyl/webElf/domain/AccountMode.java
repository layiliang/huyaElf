package com.lyl.webElf.domain;

import org.openqa.selenium.WebElement;

public class AccountMode {
	private WebElement acount;//class="udb-input udb-input-account"
	private WebElement pwd;
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
