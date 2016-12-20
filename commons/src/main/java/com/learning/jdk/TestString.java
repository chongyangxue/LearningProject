package com.learning.jdk;

import org.junit.Test;

public class TestString {
	@Test
	public void testString() {
		String s = "a" + "b" + "c" + "d";
		System.out.println(s == "abcd");
		
		String s1 = "a";
		String s2 = s1 + "b";
		String s3 = "a" + "b";
		String s4  = "ab";
		System.out.println(s2 == "ab");
		System.out.println(s3 == "ab");
		System.out.println(s3 == s4);

	}
}
