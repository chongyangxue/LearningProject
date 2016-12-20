package com.learning.guava;

import org.junit.Test;

import com.google.common.base.Objects;

public class ObjectClass {
	@Test
	public void objects(){
		System.out.println(Objects.equal("a", "b"));
		System.out.println(Objects.equal(null, "a"));
		
		String result1 = Objects.toStringHelper(new User("chongyang", "sohu")).toString();
		System.out.println(result1);

	}
	
	class User{
		private String name;
		private String company;
		
		public User(String name, String company){
			this.name = name;
			this.company = company;
		}
	}
}
