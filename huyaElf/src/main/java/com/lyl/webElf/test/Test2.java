package com.lyl.webElf.test;

public class Test2 {
	public static void main(String[] args) throws InterruptedException {
		new Test2().t();
	}
	public void t(){
		System.out.println(this.getClass().getResource("/"));
	}
}