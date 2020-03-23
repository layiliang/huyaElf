package com.lyl.webElf.base.context;

import org.springframework.stereotype.Component;

import com.lyl.webElf.utils.ChromeDriverCreater;
//@Component
public class DigBeanWebDriverContext extends DriverContext{

	
	public DigBeanWebDriverContext() {
		super();
		driver = new ChromeDriverCreater().createDriver1();
	}
}
