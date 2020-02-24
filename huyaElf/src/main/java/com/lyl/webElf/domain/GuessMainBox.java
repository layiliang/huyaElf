package com.lyl.webElf.domain;

import org.openqa.selenium.WebElement;

public class GuessMainBox {
	//竞猜主题
	private WebElement boxTitle ;//class="box-title"
	//开盘按钮
	private WebElement guessOpen;//class="guess-open"
	//竞猜按钮1：可能为种豆或马上开猜
	private WebElement guessBtn1;//class="guess-btn"
	//该选项名称
	private WebElement guessName1;//class="process-name"
	//该选项已种豆数
	private WebElement guessNum1;//class="process-num"
	private WebElement guessResult1;//class="guess-result"
	
	
	private WebElement guessBtn2;
	private WebElement guessName2;
	private WebElement guessNum2;
	private WebElement guessResult2;//class="guess-result"

	public WebElement getGuessResult1() {
		return guessResult1;
	}
	public void setGuessResult1(WebElement guessResult1) {
		this.guessResult1 = guessResult1;
	}
	public WebElement getGuessResult2() {
		return guessResult2;
	}
	public void setGuessResult2(WebElement guessResult2) {
		this.guessResult2 = guessResult2;
	}
	public WebElement getBoxTitle() {
		return boxTitle;
	}
	public void setBoxTitle(WebElement boxTitle) {
		this.boxTitle = boxTitle;
	}
	public WebElement getGuessOpen() {
		return guessOpen;
	}
	public void setGuessOpen(WebElement guessOpen) {
		this.guessOpen = guessOpen;
	}
	public WebElement getGuessBtn1() {
		return guessBtn1;
	}
	public void setGuessBtn1(WebElement guessBtn1) {
		this.guessBtn1 = guessBtn1;
	}
	public WebElement getGuessBtn2() {
		return guessBtn2;
	}
	public void setGuessBtn2(WebElement guessBtn2) {
		this.guessBtn2 = guessBtn2;
	}
	
	public WebElement getGuessName1() {
		return guessName1;
	}
	public void setGuessName1(WebElement guessName1) {
		this.guessName1 = guessName1;
	}
	public WebElement getGuessName2() {
		return guessName2;
	}
	public void setGuessName2(WebElement guessName2) {
		this.guessName2 = guessName2;
	}
	public WebElement getGuessNum1() {
		return guessNum1;
	}
	public void setGuessNum1(WebElement guessNum1) {
		this.guessNum1 = guessNum1;
	}
	public WebElement getGuessNum2() {
		return guessNum2;
	}
	public void setGuessNum2(WebElement guessNum2) {
		this.guessNum2 = guessNum2;
	}
	
}
