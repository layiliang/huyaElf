package com.lyl.webElf.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.lyl.webElf.base.context.DiggerDriverContext;
import com.lyl.webElf.consts.HuyaContext;
import com.lyl.webElf.domain.User;
import com.lyl.webElf.services.DigBeanService;
import com.lyl.webElf.services.HuyaManageService;
import com.lyl.webElf.utils.SystemUsageUtil;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DailyDigAndTask {
	List<DiggerDriverContext> diggerDriverContexts = new ArrayList<>();
	@Autowired
	DigBeanService digBeanService;
	@Autowired
	HuyaManageService huyaManageService;
	Logger logger = Logger.getLogger(DailyDigAndTask.class);
    //3.添加定时任务
    //@Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate=86400000)
    public void digTreasure() throws Exception {
    	logger.info("开始定时任务");
		for(int i = diggerDriverContexts.size()-1;i>=0;i--){
			DiggerDriverContext digDriverContext = diggerDriverContexts.get(i);
			digDriverContext.getDriver().close();
			digDriverContext.getDriver().quit();
		}
		for(User user:HuyaContext.users){
			user.setDig(false);
		}
		diggerDriverContexts = new ArrayList<>();
		while(true){
			Thread.sleep(20000);
			for(int i = 0 ; i < HuyaContext.users.size();i++){
				User user = HuyaContext.users.get(i);
				if(!user.isDig()){
					while(SystemUsageUtil.getCpuUsage()>0.9 || diggerDriverContexts.size()>20){
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
								while(true){
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
									if(diggerDriverContext.getDigTimes()>=60 && System.currentTimeMillis()-removeTime>4000000){
										huyaManageService.dailyMission(diggerDriverContext);
										logger.info("挖豆人:"+diggerDriverContext.getName()+" 结束挖豆");
										diggerDriverContext.getDriver().close();
										diggerDriverContext.getDriver().quit();
										diggerDriverContexts.remove(diggerDriverContext);
									}
									Thread.sleep(20000);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
					diggerDriverContexts.add(diggerDriverContext);
					user.setDig(true);
				}
			}
		}
	}
}