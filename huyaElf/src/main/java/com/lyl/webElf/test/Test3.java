package com.lyl.webElf.test;

import com.lyl.webElf.base.service.WebPageService;
import com.lyl.webElf.domain.HostPage;

public class Test3 {
	public static void main(String[] args) {
		TestService testService = new TestService();
		HostPage hostPage =testService.getWebPage();
		
	}
}
class TestService extends WebPageService<HostPage>{
	
}