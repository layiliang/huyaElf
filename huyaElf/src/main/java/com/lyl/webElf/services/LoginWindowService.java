package com.lyl.webElf.services;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.lyl.webElf.base.context.DriverContext;
import com.lyl.webElf.base.service.WebPageService;
import com.lyl.webElf.domain.LoginWindow;

@Service
public class LoginWindowService extends WebPageService<LoginWindow> {
	public LoginWindowService() {
		webPage = new LoginWindow();
	}

	public void initLoginWindow() throws Exception {
		initLoginWindow(defaultDriverContext);
	}

	public void initLoginWindow(DriverContext driverContext) throws Exception {
		WebDriver driver = driverContext.getDriver();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login-btn")));
		} catch (Exception e) {
			throw new Exception();
		}
		webPage.setHandle(driver.getWindowHandle());
		webPage.setLoginButton(driver.findElement(By.id("login-btn")));
		webPage.setTitle(driver.getTitle());
		webPage.setUrl(driver.getCurrentUrl());
		webPage.setAccount(driver.findElement(By.className("udb-input-account")));
		webPage.setPwd(driver.findElement(By.className("udb-input-pw")));
	}

	public void loginByAccount(String account, String pwd) throws Exception {
		loginByAccount(account, pwd, defaultDriverContext);
	}

	public void loginByAccount(String account, String pwd, DriverContext driverContext) throws Exception {
		WebDriver driver = driverContext.getDriver();
		initLoginWindow();
		webPage.getAccount().sendKeys(account);
		webPage.getPwd().sendKeys(pwd);
		webPage.getLoginButton().click();
		driver.switchTo().defaultContent();
	}

}
