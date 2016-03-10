package com.test.hazelcast;

import java.util.concurrent.locks.Lock;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class DistributedMapTest2 {

    public static void main(String[] args) throws InterruptedException {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

        while (true) {
            Lock lock = hazelcastInstance.getLock("master");
            boolean isMaster = lock.tryLock();
            if (isMaster) {
                System.out.println(hazelcastInstance.getLocalEndpoint().toString() + " is Master");
            }

            Thread.sleep(1000);
        }
    }

}