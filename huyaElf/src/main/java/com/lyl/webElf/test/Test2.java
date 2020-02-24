package com.lyl.webElf.test;

public class Test2 {
	public static void main(String[] args) {
		Zi1 z1 = new Zi1();
		Zi2 z2 = new Zi2();
		Ele ele = new Ele();
		ele.setA(1);
		Foo foo=  new Foo();
		foo.setEle(ele);
		ele.setA(15);
		System.out.println(z1.getEle().a);
		System.out.println(z2.getEle().a);
		
	}
}

class Foo{
	Ele ele =null;

	public Ele getEle() {
		return ele;
	}

	public void setEle(Ele ele) {
		this.ele = ele;
	}
	
	
}
class Zi1 extends Foo{
	
}
class Zi2 extends Foo{
	
}
class Ele{
	int a;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}
	
}