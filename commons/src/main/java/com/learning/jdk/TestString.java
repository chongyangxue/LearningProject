package com.learning.jdk;

import lombok.Data;
import org.junit.Test;

public class TestString {
	@Test
	public void testString() {
        User user = new User(10, 99.4F);
        Integer age = user.getAge();
        user.setAge(9);
        System.out.println(age);
    }

	@Data
	static class User {
        private Integer age;
        private Float score;

        public User(Integer age, Float score) {
            this.age = age;
            this.score = score;
        }
    }
}
