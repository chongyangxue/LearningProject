package com.test.hazelcast;

import java.util.Map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class DistributedMapTest {

    public static void main(String[] args) throws InterruptedException {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        Map<Integer, String> customers = hazelcastInstance.getMap("customers");
        for (int i = 0; i < 100000; i++) {
            customers.put(i, "value" + i);
        }
        System.out.println("Map Size:" + customers.size());
    }
}