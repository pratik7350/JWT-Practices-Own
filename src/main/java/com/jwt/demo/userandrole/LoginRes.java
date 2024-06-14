package com.jwt.demo.userandrole;

public class LoginRes {
	
	
	private String emailId;
	private String token;
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LoginRes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginRes(String emailId, String token) {
		super();
		this.emailId = emailId;
		this.token = token;
	}
	
	

}
