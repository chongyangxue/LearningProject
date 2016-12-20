package com.learning.common;

import java.util.Date;

public class User {
	private Integer userid;
	private String username;
	private String mobile;
	private Date startTime;

	public User(Integer userid, String username, String mobile, Date time){
		this.userid = userid;
		this.username = username;
		this.mobile = mobile;
		this.setStartTime(time);
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
