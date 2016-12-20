package com.learning.jdk;

public class overrideTestFather {
	public void print() {
		System.out.println("Father's print function.");
	}
	
	public void print(Object o) {
		System.out.println("Father's print function:" + o);
	}
}
