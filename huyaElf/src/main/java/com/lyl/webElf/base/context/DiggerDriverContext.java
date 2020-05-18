package com.lyl.webElf.base.context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lyl.webElf.utils.ChromeDriverCreater;
//@Component
public class DiggerDriverContext extends DriverContext{
	private String name;//用户姓名
	private boolean isDigging;//是否挖豆中
	private int digTimes;//挖豆次数
	private int successDigTime;//成功挖豆次数
	private int silverBean;//挖到银豆数
	private int goldBean;//挖到金豆数
	private List<Map<String, String>> successHarvest = new ArrayList<>();//成功挖豆的结果列表
	private Map<String,Long> durationMap = new HashMap<>();//打开的每个页面的打开时间
	private Calendar registerTime;//注册时间
	private Calendar startDigTime;//开始挖豆时间

	

	public Calendar getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Calendar registerTime) {
		this.registerTime = registerTime;
	}

	public Calendar getStartDigTime() {
		return startDigTime;
	}

	public void setStartDigTime(Calendar startDigTime) {
		this.startDigTime = startDigTime;
	}

	public DiggerDriverContext() {
		super();
		//driver = new PhantomjsDriverCreater().createDriver();
		driver = new ChromeDriverCreater().createDriver();
		//driver = new ChromeHeadLessDriverCreater().createDriver();
	}
	
	public List<Map<String, String>> getSuccessHarvest() {
		return successHarvest;
	}

	public void setSuccessHarvest(List<Map<String, String>> successHarvest) {
		this.successHarvest = successHarvest;
	}

	public int getSuccessDigTime() {
		return successDigTime;
	}

	public void setSuccessDigTime(int successDigTime) {
		this.successDigTime = successDigTime;
	}

	public int getSilverBean() {
		return silverBean;
	}

	public void setSilverBean(int silverBean) {
		this.silverBean = silverBean;
	}

	public int getGoldBean() {
		return goldBean;
	}

	public void setGoldBean(int goldBean) {
		this.goldBean = goldBean;
	}

	public Map<String, Long> getDurationMap() {
		return durationMap;
	}
	public void setDurationMap(Map<String, Long> durationMap) {
		this.durationMap = durationMap;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isDigging() {
		return isDigging;
	}
	public void setDigging(boolean isDigging) {
		this.isDigging = isDigging;
	}

	public int getDigTimes() {
		return digTimes;
	}

	public void setDigTimes(int digTimes) {
		this.digTimes = digTimes;
	}

	
}
