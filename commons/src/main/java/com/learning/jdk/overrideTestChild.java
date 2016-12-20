package com.learning.jdk;

public class overrideTestChild extends overrideTestFather {
	@Override
	public void print() {
		System.out.println("child's print function.");
	}

	@Override
	public void print(Object s) {
		System.out.println("child's print(Object) function: " + s);
	}

	public void print(String s) {
		System.out.println("child's print(String) function: " + s);
	}

	public void print(int i) {
		System.out.println("child's print(Int) function: " + i);
	}

	public static void main(String[] args) {
		overrideTestFather t = new overrideTestChild();
		t.print(new String("Hello World"));
		t.print(1);
	}
}
