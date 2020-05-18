package com.lyl.webElf.domain;

public class User {
	private String name;
	private String pwd;
	private String id;
	private String signUpDate;
	private boolean isDig;
	
	
	public boolean isDig() {
		return isDig;
	}
	public void setDig(boolean isDig) {
		this.isDig = isDig;
	}
	public String getSignUpDate() {
		return signUpDate;
	}
	public void setSignUpDate(String signUpDate) {
		this.signUpDate = signUpDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User(String name, String pwd, String id) {
		super();
		this.name = name;
		this.pwd = pwd;
		this.id = id;
	}
	
}
