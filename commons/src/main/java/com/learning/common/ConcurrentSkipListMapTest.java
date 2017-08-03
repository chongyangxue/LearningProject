package com.learning.common;

import org.junit.Test;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by xuechongyang on 17/8/3.
 */

public class ConcurrentSkipListMapTest {

    @Test
    public void test() {
        ConcurrentSkipListMap<Integer, String> skipListMap = new ConcurrentSkipListMap<>();
        skipListMap.put(2, "test2");
        skipListMap.put(1, "test1");
        skipListMap.put(3, "test3");
        skipListMap.entrySet().forEach(e -> System.out.println(e.getValue()));
    }
}
