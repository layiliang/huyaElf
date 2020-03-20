package com.lyl.webElf.domain;

import org.openqa.selenium.WebElement;

public class LiveItem {
	private String id;
	private String num;//class="js-num"
	private WebElement link;//class="video-info new-clickstat "
	private String title;//class="title new-clickstat"
	private boolean isOnTv;//class=tag-leftTop
	private boolean isGuess;//class=tag-leftTop
	private String hostName;
	private String type;//class="game-type fr"  a
	private String url;
	private String groupId;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LiveItem other = (LiveItem) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public boolean isGuess() {
		return isGuess;
	}
	public void setGuess(boolean isGuess) {
		this.isGuess = isGuess;
	}
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
