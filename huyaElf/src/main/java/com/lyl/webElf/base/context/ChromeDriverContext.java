package com.lyl.webElf.base.context;

import com.lyl.webElf.utils.ChromeDriverCreater;

public class  ChromeDriverContext extends DriverContext{
	
	public ChromeDriverContext() {
		super();
		driver = new ChromeDriverCreater().createDriver();
	}
}
