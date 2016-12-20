package com.learning.concurrent.old;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ConcurentModMap {
	@Test
	public void testModification() {
		Map<String, Person> map = new HashMap<String, Person>();
		map.put("p1", new Person("xuechongyang", 28));
		map.put("p2", new Person("guanghui", 28));
		map.put("p3", new Person("guochao", 29));
		Person p1 = map.get("p1");
		Person p2 = map.get("p1");
		System.out.println(p1 == p2);
		map.remove("p1");
		p2.setAge(30);
		System.out.println(p2.getAge());
	}
	
	class Person{
		private String name;
		private int age;
		
		public Person(String name, int age){
			this.name = name;
			this.age = age;
		}
		
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
}
