package com.lyl.webElf.test;

public class Test2 {
	public static void main(String[] args) {
		int num1 = 10000;
		double rate1 = 1.2;
		double rate2 = 0.95;
		int num2 = (int) (num1*(0.95*rate1 + 1)/(0.95*rate2 + 1));
		System.out.println(num1+num2);
		System.out.println(num1*(0.95*rate1 - (0.95*rate1 + 1)/(0.95*rate2 + 1)));
		System.out.println(num2*(0.95*rate2 - (0.95*rate2 + 1)/(0.95*rate1 + 1)));
		System.out.println((0.95*rate1 - (0.95*rate1 + 1)/(0.95*rate2 + 1))/(1 + (0.95*rate1 + 1)/(0.95*rate2 + 1)));
		System.out.println(151.64257555847493/(num1+num2));
		System.out.println("-------");
		System.out.println((0.95*0.95*rate1*rate2 - 1)/(0.95*rate1+0.95*rate2+2));
		
		System.out.println((0.95*rate2 - (0.95*rate2 + 1)/(0.95*rate1 + 1))/((0.95*rate2 + 1)/(0.95*rate1 + 1) + 1));
		
		System.out.println(21248/(1+(0.95*rate1 + 1)/(0.95*rate2 + 1)));
	}
}