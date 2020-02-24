package com.lyl.webElf.domain;

import java.util.Map;

import org.openqa.selenium.WebElement;

import com.lyl.webElf.base.domain.WebPage;

public class UserMainPage extends WebPage{
	private PageCommonElement pageCommonElement;
	Map<String, WebElement> menus;//class="title"   class="icon-filter" a

	public Map<String, WebElement> getMenus() {
		return menus;
	}

	public void setMenus(Map<String, WebElement> menus) {
		this.menus = menus;
	}
	

}
