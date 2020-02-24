package com.lyl.webElf.test;

import java.util.ArrayList;
import java.util.List;

import com.lyl.webElf.base.domain.WebPage;

public class Test {
	public static void main(String[] args) {
		Page<String> page = new Page<String>();
		List<String> list = new ArrayList<String>();	
		list.add("fff");
		page.t("fff");
		list.get(0);
	}
}
class Page<T>{
	 void t(T t){
		System.out.println(t);
	}
}

