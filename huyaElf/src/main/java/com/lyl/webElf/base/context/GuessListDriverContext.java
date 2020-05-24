package com.lyl.webElf.base.context;

import com.lyl.webElf.utils.ChromeDriverCreater;

//@Component
public class GuessListDriverContext extends DriverContext {
	private int pageNo;// 第几页

	public GuessListDriverContext() {
		super();
		// driver = new PhantomjsDriverCreater().createDriver();
		driver = new ChromeDriverCreater().createDriver();
		// driver = new ChromeHeadLessDriverCreater().createDriver();
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}
