package com.test.hazelcast;

import java.util.Map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class DistributedMapTest2 {

    public static void main(String[] args) throws InterruptedException {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        Map<Integer, String> customers = hazelcastInstance.getMap("customers");
        while (true) {
            System.out.println(customers.get(customers.size() - 1));
            System.out.println("Map Size:" + customers.size());
            Thread.sleep(1000);
        }
    }
}