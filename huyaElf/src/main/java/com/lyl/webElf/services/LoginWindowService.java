package com.lyl.webElf.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.lyl.webElf.base.service.WebPageService;
import com.lyl.webElf.domain.LoginWindow;
import com.lyl.webElf.domain.User;

@Service
public class LoginWindowService extends WebPageService<LoginWindow> {
	Logger logger = Logger.getLogger(WebPageService.class);
	public LoginWindowService() {
		webPage = new LoginWindow();
	}

	public void initLoginWindow() throws Exception {
		initLoginWindow(defaultDriverContext.getDriver());
	}

	public void initLoginWindow(WebDriver driver) throws Exception {
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

	public void loginByAccount(User user) throws Exception {
		loginByAccount(defaultDriverContext.getDriver(),user);
	}

	public void loginByAccount(WebDriver driver,User user) throws Exception {
		initLoginWindow(driver);
		webPage.getAccount().sendKeys(user.getId());
		webPage.getPwd().sendKeys(user.getPwd());
		JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
		Thread.sleep(1000);
		//driver.findElement(By.id("nav-login")).click();
		String clickJs = "$('#login-btn').click()";
		driver_js.executeScript(clickJs);
		//webPage.getLoginButton().click();
		driver.switchTo().defaultContent();
		Thread.sleep(30000);
		List<WebElement> title = driver.findElements(By.className("nav-user-title"));
		if(title!=null && title.size()>0 && title.get(0).getAttribute("innerText").indexOf(user.getName())>=0){
			logger.info(user.getName()+"登录成功");
		}else{
			logger.info("登录失败");
		}
		
	}
	
}
