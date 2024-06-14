package com.jwt.demo.userandrole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class HomeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String username;
	private String emailId;
	private Long mobileNo;

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	
	
	public HomeEntity(Long userId, String username, String emailId, Long mobileNo) {
		super();
		this.userId = userId;
		this.username = username;
		this.emailId = emailId;
		this.mobileNo = mobileNo;
	
	}
	
	
	public HomeEntity(String emailId) {
		super();
		this.emailId = emailId;
	}
	
	@Override
	public String toString() {
		return "HomeEntity [userId=" + userId + ", username=" + username + ", emailId=" + emailId + ", mobileNo="
				+ mobileNo + "]";
	}
	public HomeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	

}
