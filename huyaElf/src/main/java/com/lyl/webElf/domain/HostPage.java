package com.lyl.webElf.domain;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import com.lyl.webElf.base.domain.WebPage;

public class HostPage extends WebPage{
	private boolean isSubscribed;
	private  WebElement hostName;  //class host-name
	private PageCommonElement pageCommonElement;
	//全屏
	private WebElement fullscreenBtn;//id="player-fullscreen-btn"
	//输入框
	private WebElement msgInput;//id="pub_msg_input"
	//发送按钮
	private WebElement msgSendButton;//id="msg_send_bt"
	//订阅按钮
	private WebElement subscribeBtn;//id="activityCount";
	//确认取消订阅
	private WebElement confirmToDisSubscribe ;//class="subscribe-layer-cancel-control" span 0确定
	//取消取消订阅
	private WebElement cancelToDisSubscribe ;//class="subscribe-layer-cancel-control" span 1取消
	
	//播放控制栏
	private WebElement playerCtrlWrap ;//id="player-ctrl-wrap"
	//礼物竞猜，全屏后显示
	private WebElement giftShowBtn ;//id="gift-show-btn"
	//玩竞猜按钮
	private WebElement guessBox ;//class="guess-box"
	//礼物列表
	private List<WebElement> gifts;
	//竞猜列表
	private List<GuessMainBox> guessMainBoxs;//class="guess-main-box"
	//清晰度列表
	private List<WebElement> playerVideoTypes;//class="player-videotype-list" li
	//百宝箱
	private WebElement playerBox;//id="player-box"
	//领取按钮
	private List<WebElement> playerBoxStat3s;//class="player-box-stat3"
	//数量
	private List<WebElement> playerBoxStat4s;//class="player-box-stat4"
	//关闭竞猜结果按钮,有参与竞猜才会出现
	private WebElement guessResultBottom;//class="guess-result-bottom" span
	//当前的清晰度
	private WebElement playerVideotypeCur;//class="player-videotype-cur"
	//清晰度面板
	private WebElement playerMenuPanel;//class="player-menu-panel"
	//关闭竞猜按钮
	private WebElement guessMainClose;//class="guess-main-close"
	//TODO
	private WebElement closeCreateLayer;//class="close-create-layer"
	//竞猜面板
	private GuessPlan guessPlan;
	//开盘面板
	private PlayerPanelGuessOpen playerPanelGuessOpen;
	
	
	public WebElement getHostName() {
		return hostName;
	}
	public void setHostName(WebElement hostName) {
		this.hostName = hostName;
	}
	public boolean isSubscribed() {
		return isSubscribed;
	}
	public void setSubscribed(boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}
	public PageCommonElement getPageCommonElement() {
		return pageCommonElement;
	}
	public void setPageCommonElement(PageCommonElement pageCommonElement) {
		this.pageCommonElement = pageCommonElement;
	}
	public WebElement getCloseCreateLayer() {
		return closeCreateLayer;
	}
	public void setCloseCreateLayer(WebElement closeCreateLayer) {
		this.closeCreateLayer = closeCreateLayer;
	}
	public List<WebElement> getPlayerBoxStat3s() {
		return playerBoxStat3s;
	}
	public void setPlayerBoxStat3s(List<WebElement> playerBoxStat3s) {
		this.playerBoxStat3s = playerBoxStat3s;
	}

	public List<WebElement> getPlayerBoxStat4s() {
		return playerBoxStat4s;
	}
	public void setPlayerBoxStat4s(List<WebElement> playerBoxStat4s) {
		this.playerBoxStat4s = playerBoxStat4s;
	}
	public WebElement getPlayerCtrlWrap() {
		return playerCtrlWrap;
	}
	public void setPlayerCtrlWrap(WebElement playerCtrlWrap) {
		this.playerCtrlWrap = playerCtrlWrap;
	}
	public WebElement getGiftShowBtn() {
		return giftShowBtn;
	}
	public void setGiftShowBtn(WebElement giftShowBtn) {
		this.giftShowBtn = giftShowBtn;
	}
	public WebElement getGuessBox() {
		return guessBox;
	}
	public void setGuessBox(WebElement guessBox) {
		this.guessBox = guessBox;
	}


	public WebElement getCancelToDisSubscribe() {
		return cancelToDisSubscribe;
	}
	public void setCancelToDisSubscribe(WebElement cancelToDisSubscribe) {
		this.cancelToDisSubscribe = cancelToDisSubscribe;
	}
	public List<WebElement> getGifts() {
		return gifts;
	}
	public void setGifts(List<WebElement> gifts) {
		this.gifts = gifts;
	}

	public List<GuessMainBox> getGuessMainBoxs() {
		return guessMainBoxs;
	}
	public void setGuessMainBoxs(List<GuessMainBox> guessMainBoxs) {
		this.guessMainBoxs = guessMainBoxs;
	}

	public List<WebElement> getPlayerVideoTypes() {
		return playerVideoTypes;
	}
	public void setPlayerVideoTypes(List<WebElement> playerVideoTypes) {
		this.playerVideoTypes = playerVideoTypes;
	}
	public WebElement getPlayerBox() {
		return playerBox;
	}
	public void setPlayerBox(WebElement playerBox) {
		this.playerBox = playerBox;
	}

	public WebElement getGuessResultBottom() {
		return guessResultBottom;
	}
	public void setGuessResultBottom(WebElement guessResultBottom) {
		this.guessResultBottom = guessResultBottom;
	}
	public WebElement getPlayerVideotypeCur() {
		return playerVideotypeCur;
	}
	public void setPlayerVideotypeCur(WebElement playerVideotypeCur) {
		this.playerVideotypeCur = playerVideotypeCur;
	}
	public WebElement getPlayerMenuPanel() {
		return playerMenuPanel;
	}
	public void setPlayerMenuPanel(WebElement playerMenuPanel) {
		this.playerMenuPanel = playerMenuPanel;
	}
	public WebElement getGuessMainClose() {
		return guessMainClose;
	}
	public void setGuessMainClose(WebElement guessMainClose) {
		this.guessMainClose = guessMainClose;
	}
	public GuessPlan getGuessPlan() {
		return guessPlan;
	}
	public void setGuessPlan(GuessPlan guessPlan) {
		this.guessPlan = guessPlan;
	}
	public PlayerPanelGuessOpen getPlayerPanelGuessOpen() {
		return playerPanelGuessOpen;
	}
	public void setPlayerPanelGuessOpen(PlayerPanelGuessOpen playerPanelGuessOpen) {
		this.playerPanelGuessOpen = playerPanelGuessOpen;
	}

	public WebElement getFullscreenBtn() {
		return fullscreenBtn;
	}
	public void setFullscreenBtn(WebElement fullscreenBtn) {
		this.fullscreenBtn = fullscreenBtn;
	}
	public WebElement getMsgInput() {
		return msgInput;
	}
	public void setMsgInput(WebElement msgInput) {
		this.msgInput = msgInput;
	}
	public WebElement getMsgSendButton() {
		return msgSendButton;
	}
	public void setMsgSendButton(WebElement msgSendButton) {
		this.msgSendButton = msgSendButton;
	}

	public WebElement getSubscribeBtn() {
		return subscribeBtn;
	}
	public void setSubscribeBtn(WebElement subscribeBtn) {
		this.subscribeBtn = subscribeBtn;
	}
	public WebElement getConfirmToDisSubscribe() {
		return confirmToDisSubscribe;
	}
	public void setConfirmToDisSubscribe(WebElement confirmToDisSubscribe) {
		this.confirmToDisSubscribe = confirmToDisSubscribe;
	}
}
