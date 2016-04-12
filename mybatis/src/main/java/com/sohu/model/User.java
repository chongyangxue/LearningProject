/**
 * 
 */
package com.sohu.model;

import java.io.Serializable;

/**
 * @author zhouhe
 * @since 下午5:25:22
 */
public class User implements Serializable {
	private static final long serialVersionUID = -6248248960110207349L;

	private String name;
	private String mobile;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
