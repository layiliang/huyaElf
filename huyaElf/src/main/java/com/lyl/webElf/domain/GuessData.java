package com.lyl.webElf.domain;

import java.util.Date;

public class GuessData {
	//主键
	private String id;
	//每个竞猜的id
	private String guessId;
	//创建时间
	private Date createTime;
	//创建时间
	private Date recordTime;
	//比率1
	private double rate1;//class="guess-btn"
	//该选项已种豆数
	private int num1;//class="process-num"
	private double rate2;
	private int num2;
	
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGuessId() {
		return guessId;
	}
	public void setGuessId(String guessId) {
		this.guessId = guessId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public double getRate1() {
		return rate1;
	}
	public void setRate1(double rate1) {
		this.rate1 = rate1;
	}
	public int getNum1() {
		return num1;
	}
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	public double getRate2() {
		return rate2;
	}
	public void setRate2(double rate2) {
		this.rate2 = rate2;
	}
	public int getNum2() {
		return num2;
	}
	public void setNum2(int num2) {
		this.num2 = num2;
	}


	
	
}
