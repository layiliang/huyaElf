package com.lyl.webElf.domain;

import org.openqa.selenium.WebElement;

public class GuessPlan {
	//竞猜主题
	private WebElement title;//class="choice-color"
	//剩余压豆数
	private WebElement leftBean;//class="left-bean"
	//我的豆子数
	private WebElement myBean;//class="my-bean"
	//赢则赚
	private WebElement winBean;//class="win-bean"
	//压豆输入框
	private WebElement beanInput;//class="guess-plan" input
	//确认种豆按钮
	private WebElement btn;//class="guess-plan" button
	//关闭按钮
	private WebElement close;//class="guess-plan" "close"
	
	public WebElement getClose() {
		return close;
	}
	public void setClose(WebElement close) {
		this.close = close;
	}
	public WebElement getTitle() {
		return title;
	}
	public void setTitle(WebElement title) {
		this.title = title;
	}
	public WebElement getLeftBean() {
		return leftBean;
	}
	public void setLeftBean(WebElement leftBean) {
		this.leftBean = leftBean;
	}
	public WebElement getMyBean() {
		return myBean;
	}
	public void setMyBean(WebElement myBean) {
		this.myBean = myBean;
	}
	public WebElement getWinBean() {
		return winBean;
	}
	public void setWinBean(WebElement winBean) {
		this.winBean = winBean;
	}
	public WebElement getBeanInput() {
		return beanInput;
	}
	public void setBeanInput(WebElement beanInput) {
		this.beanInput = beanInput;
	}
	public WebElement getBtn() {
		return btn;
	}
	public void setBtn(WebElement btn) {
		this.btn = btn;
	}
	
	
	
}
