/** 
 * File: PubSub.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package redis.pubsub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Description: 
 * Author: Sachiel 
 * Date: 2016-1-18 
 */
public class Sub {
    
    @Test
    public void sub() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        final JedisPool jedisPool = new JedisPool(poolConfig, "192.168.6.148", 6379, 0);
        final Jedis subscriberJedis = jedisPool.getResource();
        final Subscriber subscriber = new Subscriber();
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Subscribing to \"channel\". This thread will be blocked.");
                    subscriberJedis.subscribe(subscriber, "channel");
                    System.out.println("Subscription ended.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        while (true) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
