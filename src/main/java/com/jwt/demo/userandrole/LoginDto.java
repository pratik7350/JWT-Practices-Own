package com.jwt.demo.userandrole;

public class LoginDto {
	
	private String emailId;

	public LoginDto(String emailId) {
		super();
		this.emailId = emailId;
		
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public LoginDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
