package com.lyl.webElf.test;

import com.lyl.webElf.utils.ChromeDriverCreater;

public class Test2 {
	public static void main(String[] args) throws InterruptedException {
		for(int i = 0 ; i < 100;i++){
			new ChromeDriverCreater().createDriver();
		}
	}
}