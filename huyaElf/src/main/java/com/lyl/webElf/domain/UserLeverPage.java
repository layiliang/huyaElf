package com.lyl.webElf.domain;

import java.util.List;

import org.openqa.selenium.WebElement;

public class UserLeverPage extends UserMainPage{
	private List<WebElement> receiveList;//class="task-status ststus-receive"
	private WebElement btnClose;//class="btn-close J_btnClose"
	public List<WebElement> getReceiveList() {
		return receiveList;
	}
	public void setReceiveList(List<WebElement> receiveList) {
		this.receiveList = receiveList;
	}
	public WebElement getBtnClose() {
		return btnClose;
	}
	public void setBtnClose(WebElement btnClose) {
		this.btnClose = btnClose;
	}
}
