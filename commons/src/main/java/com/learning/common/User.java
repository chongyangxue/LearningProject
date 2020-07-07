package com.learning.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class User {
	private Integer userid;
	private String username;
	private String mobile;
	private Date startTime;

}
