package com.lyl.webElf.test;

public class TestA<T> {
	private T t;
	
	public void setT(T t) {
		this.t = t;
	}

	public T getT() {
		return t;
	}

	public static void main(String[] args) {
		TestA<TestB> a = new TestA<TestB>();
		System.out.println(a.getT());// 此时打印为null
	}
}

class TestB {

}