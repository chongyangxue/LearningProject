package com.learning.concurrent.old;

import java.util.Map;
import java.util.Random;

import org.junit.Test;

import com.google.common.collect.Maps;

public class ConcurentModMap2 {
    
	@Test
	public void testModification() {
		final Map<Integer, String> map = Maps.newConcurrentMap();
		for (int i = 0; i < 100000; i++) {
		    map.put(i, "value_" + i);
		}
		
		new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    map.remove(new Random().nextInt(10000));
                }
            }
		}).start();
		
		int i = 0;
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
//		    System.out.println(entry.getKey() + " : " + entry.getValue());
		    System.out.println(i++);
		}
	}
	
}
