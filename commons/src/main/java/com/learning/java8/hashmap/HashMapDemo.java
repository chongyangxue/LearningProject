package com.learning.java8.hashmap;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xuechongyang on 17/1/10.
 */
public class HashMapDemo {

    @Test
    public void testHashMap() {
        HashMap map = new HashMap();
        map.put("name", null);
    }

    @Test
    public void test() {
        ConcurrentHashMap map = new ConcurrentHashMap();
        map.put("name", "zhangsan");
        map.put("age", 123);
        System.out.println(map.get("name"));
    }
}
