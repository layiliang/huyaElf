package com.lyl.webElf.domain;

import java.util.Date;

public class GuessResult {
	private String id;
	private String gameId;
	private String title;
	private Date createTime;
	private double endRate1;
	private int endNum1;
	private String guessName1;
	private String result1;
	private double endRate2;
	private int endNum2;
	private String guessName2;
	private String result2;
	private String hostName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public double getEndRate1() {
		return endRate1;
	}
	public void setEndRate1(double endRate1) {
		this.endRate1 = endRate1;
	}
	public int getEndNum1() {
		return endNum1;
	}
	public void setEndNum1(int endNum1) {
		this.endNum1 = endNum1;
	}
	public String getGuessName1() {
		return guessName1;
	}
	public void setGuessName1(String guessName1) {
		this.guessName1 = guessName1;
	}
	public String getResult1() {
		return result1;
	}
	public void setResult1(String result1) {
		this.result1 = result1;
	}
	public double getEndRate2() {
		return endRate2;
	}
	public void setEndRate2(double endRate2) {
		this.endRate2 = endRate2;
	}
	public int getEndNum2() {
		return endNum2;
	}
	public void setEndNum2(int endNum2) {
		this.endNum2 = endNum2;
	}
	public String getGuessName2() {
		return guessName2;
	}
	public void setGuessName2(String guessName2) {
		this.guessName2 = guessName2;
	}
	public String getResult2() {
		return result2;
	}
	public void setResult2(String result2) {
		this.result2 = result2;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	
}
