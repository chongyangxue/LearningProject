package com.learning.ListCopy;

public class User {
	private Integer userid;
	private String username;
	private String mobile;
	private String address;

	public User(Integer userid, String username, String mobile, String address){
		this.userid = userid;
		this.username = username;
		this.mobile = mobile;
		this.address = address;
	}
	
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
