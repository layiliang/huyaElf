package com.lyl.webElf.domain;

import java.util.Date;

public class GuessData {
	//主播名称
	private String hostName;
	//主键
	private String id;
	//每盘的id
	private String gameId;
	//创建时间
	private Date createTime;
	//竞猜主题
	private String boxTitle ;//class="box-title"
	//比率1
	private String rate1;//class="guess-btn"
	//该选项名称
	private String guessName1;//class="process-name"
	//该选项已种豆数
	private String guessNum1;//class="process-num"
	//竞猜结果1
	private String guessResult1;

	private String guessResult2;
	
	private String rate2;
	private String guessName2;
	private String guessNum2;
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getBoxTitle() {
		return boxTitle;
	}
	public void setBoxTitle(String boxTitle) {
		this.boxTitle = boxTitle;
	}
	public String getRate1() {
		return rate1;
	}
	public void setRate1(String rate1) {
		this.rate1 = rate1;
	}
	public String getGuessName1() {
		return guessName1;
	}
	public void setGuessName1(String guessName1) {
		this.guessName1 = guessName1;
	}
	public String getGuessNum1() {
		return guessNum1;
	}
	public void setGuessNum1(String guessNum1) {
		this.guessNum1 = guessNum1;
	}
	public String getGuessResult1() {
		return guessResult1;
	}
	public void setGuessResult1(String guessResult1) {
		this.guessResult1 = guessResult1;
	}
	public String getGuessResult2() {
		return guessResult2;
	}
	public void setGuessResult2(String guessResult2) {
		this.guessResult2 = guessResult2;
	}
	public String getRate2() {
		return rate2;
	}
	public void setRate2(String rate2) {
		this.rate2 = rate2;
	}
	public String getGuessName2() {
		return guessName2;
	}
	public void setGuessName2(String guessName2) {
		this.guessName2 = guessName2;
	}
	public String getGuessNum2() {
		return guessNum2;
	}
	public void setGuessNum2(String guessNum2) {
		this.guessNum2 = guessNum2;
	}
	
	
}
