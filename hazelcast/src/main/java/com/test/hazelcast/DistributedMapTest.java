package com.test.hazelcast;

import java.util.concurrent.locks.Lock;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class DistributedMapTest {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("hazelcast.config", "/opt/gw/broker/conf/hazelcast.xml");
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

        while (true) {
            Lock lock = hazelcastInstance.getLock("master");
            boolean isMaster = lock.tryLock();
            if (isMaster) {
                //System.out.println(hazelcastInstance.getLocalEndpoint().toString() + " is Master");
            }

            Thread.sleep(1000);
        }
    }
   
}