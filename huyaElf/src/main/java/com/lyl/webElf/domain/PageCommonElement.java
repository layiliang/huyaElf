package com.lyl.webElf.domain;

import org.openqa.selenium.WebElement;

import com.lyl.webElf.base.domain.WebPage;

public class PageCommonElement{
	private WebElement loginBtn;//id="nav-login"
	private WebElement curUser;//id="login-username"
	private WebElement exitBtn;//class="btn-exit"
	private String mybean;//id="J_huyaNavUserCardAssetsSb"
	public String getMybean() {
		return mybean;
	}
	public void setMybean(String mybean) {
		this.mybean = mybean;
	}
	public WebElement getLoginBtn() {
		return loginBtn;
	}
	public void setLoginBtn(WebElement loginBtn) {
		this.loginBtn = loginBtn;
	}
	
	public WebElement getCurUser() {
		return curUser;
	}
	public void setCurUser(WebElement curUser) {
		this.curUser = curUser;
	}
	public WebElement getExitBtn() {
		return exitBtn;
	}
	public void setExitBtn(WebElement exitBtn) {
		this.exitBtn = exitBtn;
	}
	
	
}
