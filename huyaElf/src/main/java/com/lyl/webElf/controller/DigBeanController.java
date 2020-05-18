package com.lyl.webElf.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.webElf.base.context.DiggerDriverContext;
import com.lyl.webElf.consts.HuyaContext;
import com.lyl.webElf.domain.User;
import com.lyl.webElf.services.DigBeanService;
import com.lyl.webElf.services.HuyaManageService;
import com.lyl.webElf.utils.SystemUsageUtil;

@Controller
@RequestMapping("digBean")
public class DigBeanController {
	@Autowired
	DigBeanService digBeanService;
	@Autowired
	HuyaManageService huyaManageService;
	//List<User> users = new ArrayList<User>();
	List<DiggerDriverContext> diggerDriverContexts;
	Logger logger = Logger.getLogger(DigBeanController.class);


	@RequestMapping("findTreasure")
	@ResponseBody
	public void findTreasure() throws Exception {
		//http://localhost:8080/digBean/register?id=2253812186&pwd=huya1234&name=%E6%8B%89%E4%BC%8A%E4%BA%AE
		//http://localhost:8080/digBean/register?id=2295451338&pwd=huya123&name=Beasty
		User user = new User("夏夜、、", "MMxph550701", "hy_16226407");
		//User user = new User("拉伊亮", "huya1234", "2253812186");
		//User user = new User("Beasty", "huya123", "2295451338");
		DiggerDriverContext digDriverContext = new DiggerDriverContext();
		digDriverContext.setName(user.getName());
		WebDriver driver = digDriverContext.getDriver();
		Map<String,String> handles = digDriverContext.getHandles();
		digBeanService.digOpen(driver,digDriverContext.getHandles());
		Thread.sleep(60000);
		huyaManageService.login(driver,user);
		Thread.sleep(5000);

		digBeanService.delVideoJs(driver);
		long start = System.currentTimeMillis();
		long refreshStart = System.currentTimeMillis();
		while(true){
			Thread.sleep(1000);
			digBeanService.find(digDriverContext);//主页面寻找宝藏
			
			long time = System.currentTimeMillis()-start;
			long refreshTime = System.currentTimeMillis()-refreshStart;
			if(time>20000){
				start = System.currentTimeMillis();
				digBeanService.remove(digDriverContext);//主页面挖豆以及移除宝藏
			}
			if(refreshTime>1800000){
				refreshStart = System.currentTimeMillis();
				digBeanService.refresh(digDriverContext);//主页面刷新
			}
			if(!driver.getWindowHandle().equals(handles.get("https://www.huya.com/bdcmovie"))){
				driver.switchTo().window(handles.get("https://www.huya.com/bdcmovie"));
			}
		}
	}
	@RequestMapping("register")
	@ResponseBody
	public void register(User user) throws Exception {
		HuyaContext.users.add(user);
		/*DiggerDriverContext digDriverContext = new DiggerDriverContext();
		digDriverContext.setName(user.getName());
		WebDriver driver = digDriverContext.getDriver();
		digOpen(driver,digDriverContext.getHandles());
		Thread.sleep(60000);
		huyaManageService.login(driver,user);
		Thread.sleep(5000);
		digBeanService.addHref(driver);
		digBeanService.delVideoJs(driver);
		Thread.sleep(5000);
		digDriverContext.setRegisterTime(Calendar.getInstance());
		HuyaContext.digDriverContextMap.put(user.getName(),digDriverContext);*/
	}
	@RequestMapping("unRegister")
	@ResponseBody
	public void unRegister(String name) throws Exception {
		WebDriver driver = HuyaContext.digDriverContextMap.get(name).getDriver();
		Thread.sleep(1000);
		driver.close();
		Thread.sleep(1000);
		driver.quit();
		HuyaContext.digDriverContextMap.remove(name);
	}
	/*@RequestMapping("digTreasure")
	@ResponseBody
	public void digTreasure() throws Exception {
		for(DiggerDriverContext digDriverContext:diggerDriverContexts){
			digDriverContext.getDriver().close();
			digDriverContext.getDriver().quit();
		}
		diggerDriverContexts = new ArrayList<>();
		for(User user : HuyaContext.users){
			while(SystemUsageUtil.getCpuUsage()>0.9 && diggerDriverContexts.size()>50){
				Thread.sleep(1000);
			}
			DiggerDriverContext diggerDriverContext = new DiggerDriverContext();
			diggerDriverContext.setName(user.getName());
			WebDriver driver = diggerDriverContext.getDriver();
			digBeanService.digOpen(driver,diggerDriverContext.getHandles());
			Thread.sleep(60000);
			huyaManageService.login(driver,user);
			Thread.sleep(5000);
			digBeanService.addHref(driver);
			digBeanService.delVideoJs(driver);
			Thread.sleep(5000);
			diggerDriverContext.setRegisterTime(Calendar.getInstance());
			new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						long startForRefresh = System.currentTimeMillis();
						long startForClose = System.currentTimeMillis();
						long startForRemove = System.currentTimeMillis();
						Thread.sleep(20000);
						while(SystemUsageUtil.getCpuUsage()>0.9){
							Thread.sleep(1000);
						}
						digBeanService.digBean(diggerDriverContext);//挖宝
						long refreshTime = System.currentTimeMillis()-startForRefresh;
						long closeTime = System.currentTimeMillis()-startForClose;
						long removeTime = System.currentTimeMillis()-startForRemove;
						if(closeTime>60000){
							startForClose = System.currentTimeMillis();
							digBeanService.closeFinishedTab(diggerDriverContext);//关闭已经结束挖豆的标签页
						}
						if(refreshTime>1800000){
							startForRefresh = System.currentTimeMillis();
							digBeanService.digDriverRefresh(diggerDriverContext);//刷新
						}
						if(removeTime>600000 ){
							startForRemove = System.currentTimeMillis();
							if(diggerDriverContext.getDigTimes()>=60 && System.currentTimeMillis()-diggerDriverContext.getRegisterTime().getTimeInMillis()>4000000){
								logger.info("挖豆人:"+diggerDriverContext.getName()+" 结束挖豆");
								diggerDriverContext.getDriver().close();
								diggerDriverContext.getDriver().quit();
								diggerDriverContexts.remove(diggerDriverContext);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			diggerDriverContexts.add(diggerDriverContext);
		}
	}*/
	@RequestMapping("cpuAndMemoryUse")
	@ResponseBody
	public void cpuAndMemoryUse() throws Exception {
		double maxCpuUsage=0;
		double maxMemoryUsage=0;
		int maxCpu=0;
		while(true){
			Thread.sleep(5000);
			logger.info("当前cpu使用率"+SystemUsageUtil.getCpuUsage()+" 时间："+new Date());
			logger.info("当前内存使用率"+(1-SystemUsageUtil.getMemoryUsage()));
			if(maxCpuUsage<SystemUsageUtil.getCpuUsage()){
				maxCpuUsage = SystemUsageUtil.getCpuUsage();
			}
			if(SystemUsageUtil.getCpuUsage()>80){
				maxCpu++;
				logger.info("超过80的次数为："+maxCpu);
			}
			logger.info("最大cpu使用率"+maxCpuUsage);
			if(maxMemoryUsage<(1-SystemUsageUtil.getMemoryUsage())){
				maxMemoryUsage = (1-SystemUsageUtil.getMemoryUsage());
			}
			logger.info("最大cpu使用率"+maxMemoryUsage);
		}
	}
	@RequestMapping("dailyMission")
	@ResponseBody
	public void dailyMission() throws Exception {
		User user = new User("夏夜、、", "MMxph550701", "hy_16226407");
		//User user = new User("Beasty", "huya123", "2295451338");
		DiggerDriverContext digDriverContext = new DiggerDriverContext();
		digDriverContext.setName(user.getName());
		WebDriver driver = digDriverContext.getDriver();
		digBeanService.digOpen(driver,digDriverContext.getHandles());
		Thread.sleep(60000);
		huyaManageService.login(driver,user);
		Thread.sleep(5000);
		huyaManageService.dailyMission(digDriverContext);
	}
	public static void main(String[] args) throws InterruptedException {
		while(true){
			Thread.sleep(1000);
			System.out.println("当前cpu使用率"+SystemUsageUtil.getCpuUsage());
			System.out.println("当前内存使用率"+SystemUsageUtil.getMemoryUsage());
		}
	}
}
