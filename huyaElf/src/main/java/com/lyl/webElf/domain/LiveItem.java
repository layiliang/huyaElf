package com.lyl.webElf.domain;

import org.openqa.selenium.WebElement;

public class LiveItem {
	private String num;//class="js-num"
	private WebElement link;//class="video-info new-clickstat "
	private String title;//class="title new-clickstat"
	private boolean isOnTv;//class=tag-leftTop
	private String hostName;
	private String type;//class="game-type fr"  a
	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public WebElement getLink() {
		return link;
	}
	public void setLink(WebElement link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isOnTv() {
		return isOnTv;
	}
	public void setOnTv(boolean isOnTv) {
		this.isOnTv = isOnTv;
	}
	
}
