package com.lyl.webElf.domain;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import com.lyl.webElf.base.domain.WebPage;

public class LivePage extends WebPage{
	private PageCommonElement pageCommonElement;
	private String totalPage;//class="laypage_last"
	private String curPage;//class="laypage_curr"
	private List<LiveItem> lives;//class="game-live-item"
	private WebElement prePage;//class="laypage_prev"
	private WebElement nextPage;//class="laypage_next"
	
	public PageCommonElement getPageCommonElement() {
		return pageCommonElement;
	}
	public void setPageCommonElement(PageCommonElement pageCommonElement) {
		this.pageCommonElement = pageCommonElement;
	}
	public WebElement getPrePage() {
		return prePage;
	}
	public void setPrePage(WebElement prePage) {
		this.prePage = prePage;
	}
	public WebElement getNextPage() {
		return nextPage;
	}
	public void setNextPage(WebElement nextPage) {
		this.nextPage = nextPage;
	}

	public String getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
	public String getCurPage() {
		return curPage;
	}
	public void setCurPage(String curPage) {
		this.curPage = curPage;
	}
	public List<LiveItem> getLives() {
		return lives;
	}
	public void setLives(List<LiveItem> lives) {
		this.lives = lives;
	}

	
}
