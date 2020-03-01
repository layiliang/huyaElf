package com.lyl.webElf.base.context;

import com.lyl.webElf.utils.ChromeHeadLessDriverCreater;

public class  ChromeHeadLessDriverContext extends DriverContext{
	
	public ChromeHeadLessDriverContext() {
		super();
		driver = new ChromeHeadLessDriverCreater().createDriver();
	}
}
