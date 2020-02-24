package com.lyl.webElf.domain;

import java.util.Map;

import org.openqa.selenium.WebElement;

import com.lyl.webElf.base.domain.WebPage;

public class LoginWindow extends WebPage{
	private WebElement wxLogin;
	private WebElement qqLogin;
	private WebElement weiboLogin;
	private WebElement yyLogin;
	private WebElement accountMode;
	private WebElement phoneMode;
	private WebElement loginButton;//id="login-btn"
	private WebElement getPhoneCode;
	private WebElement phoneNo;
	private WebElement phoneCode;
	private WebElement account;//class="udb-input udb-input-account"
	private WebElement pwd;//class="udb-input udb-input-pw"
	public WebElement getWxLogin() {
		return wxLogin;
	}
	public void setWxLogin(WebElement wxLogin) {
		this.wxLogin = wxLogin;
	}
	public WebElement getQqLogin() {
		return qqLogin;
	}
	public void setQqLogin(WebElement qqLogin) {
		this.qqLogin = qqLogin;
	}
	public WebElement getWeiboLogin() {
		return weiboLogin;
	}
	public void setWeiboLogin(WebElement weiboLogin) {
		this.weiboLogin = weiboLogin;
	}
	public WebElement getYyLogin() {
		return yyLogin;
	}
	public void setYyLogin(WebElement yyLogin) {
		this.yyLogin = yyLogin;
	}
	public WebElement getAccountMode() {
		return accountMode;
	}
	public void setAccountMode(WebElement accountMode) {
		this.accountMode = accountMode;
	}
	public WebElement getPhoneMode() {
		return phoneMode;
	}
	public void setPhoneMode(WebElement phoneMode) {
		this.phoneMode = phoneMode;
	}
	public WebElement getLoginButton() {
		return loginButton;
	}
	public void setLoginButton(WebElement loginButton) {
		this.loginButton = loginButton;
	}
	public WebElement getGetPhoneCode() {
		return getPhoneCode;
	}
	public void setGetPhoneCode(WebElement getPhoneCode) {
		this.getPhoneCode = getPhoneCode;
	}
	public WebElement getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(WebElement phoneNo) {
		this.phoneNo = phoneNo;
	}
	public WebElement getPhoneCode() {
		return phoneCode;
	}
	public void setPhoneCode(WebElement phoneCode) {
		this.phoneCode = phoneCode;
	}

	public WebElement getAccount() {
		return account;
	}
	public void setAccount(WebElement account) {
		this.account = account;
	}
	public WebElement getPwd() {
		return pwd;
	}
	public void setPwd(WebElement pwd) {
		this.pwd = pwd;
	}


	
	
}
