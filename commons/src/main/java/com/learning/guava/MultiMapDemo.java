/** 
 * File: MultiMapDemo.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.guava;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.junit.Test;

/**
 * Description: Author: Sachiel Date: 2016-1-18
 */
public class MultiMapDemo {
    private Multimap<String, User> multiMap = HashMultimap.create();
    
    @Test
    public void testMultiMap() {
        User user1 = new User("xue", "beijing");
        User user2 = new User("xue", "shanxi");
        
        multiMap.put("xue", user1);
        multiMap.put("xue", user2);
        
        User user3 = new User("yang", "shanghai");
        
        for(User user : multiMap.get("xue")) {
            if (user.getAddr().equals("beijing")) {
                user.setAddr(user3.getAddr());
                user.setName(user3.getName());
            }
        }

        for (User user : multiMap.values()) {
            System.out.println(user.getAddr());
        }
    }

    private static class User {
        private String name;
        private String addr;

        public User(String name, String addr) {
            this.name = name;
            this.addr = addr;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }
    }
}
