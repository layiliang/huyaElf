package com.lyl.webElf.base.context;

import com.lyl.webElf.utils.DriverUtil;

public class  DefaultDriverContext extends DriverContext{
	
	public DefaultDriverContext() {
		super();
		driver = DriverUtil.getDefaultDriver();
	}
}
