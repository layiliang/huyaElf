package com.lyl.webElf.domain;

import org.openqa.selenium.WebElement;

public class PlayerPanelGuessOpen {
	//关闭按钮
	private WebElement playerPanelClose;//class="player-panel-close"
	//押胜方单选按钮1
	private WebElement guessOpenRadio1;//class="guess-open-radio"
	//押胜方单选按钮2
	private WebElement guessOpenRadio2;//class="guess-open-radio"
	//比率输入框
	private WebElement guessRate;//class="guess-open-input" 0
	//底金输入框
	private WebElement guessAmount;//class="guess-open-input" 1
	//确认按钮
	private WebElement guessOpenConfirm;//class="guess-open-confirm"
	public WebElement getPlayerPanelClose() {
		return playerPanelClose;
	}
	public void setPlayerPanelClose(WebElement playerPanelClose) {
		this.playerPanelClose = playerPanelClose;
	}
	public WebElement getGuessOpenRadio1() {
		return guessOpenRadio1;
	}
	public void setGuessOpenRadio1(WebElement guessOpenRadio1) {
		this.guessOpenRadio1 = guessOpenRadio1;
	}
	public WebElement getGuessOpenRadio2() {
		return guessOpenRadio2;
	}
	public void setGuessOpenRadio2(WebElement guessOpenRadio2) {
		this.guessOpenRadio2 = guessOpenRadio2;
	}
	public WebElement getGuessRate() {
		return guessRate;
	}
	public void setGuessRate(WebElement guessRate) {
		this.guessRate = guessRate;
	}
	public WebElement getGuessAmount() {
		return guessAmount;
	}
	public void setGuessAmount(WebElement guessAmount) {
		this.guessAmount = guessAmount;
	}
	public WebElement getGuessOpenConfirm() {
		return guessOpenConfirm;
	}
	public void setGuessOpenConfirm(WebElement guessOpenConfirm) {
		this.guessOpenConfirm = guessOpenConfirm;
	}
	
}
