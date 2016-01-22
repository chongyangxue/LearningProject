/** 
 * File: PubSub.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package redis.pubsub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Description: 
 * Author: Sachiel 
 * Date: 2016-1-18 
 */
public class Pub {
    
    private int total = 100000;
    
    @Test
    public void pub() {
        final JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "192.168.6.148", 6379, 0);
        long startTime=System.currentTimeMillis();   //获取开始时间  
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        final AtomicInteger count = new AtomicInteger(total);
        
        System.out.println("开始写入测试");
        for (int i = 0; i < 10; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Jedis publisherJedis = jedisPool.getResource();
                    while (count.getAndDecrement() > 0) 
                        publisherJedis.publish("channel", "pub_" + count.get());
                    }
                });
        }
        
        while (true) {
            if(count.get() < 0) {
                long endTime=System.currentTimeMillis();  
                System.out.println(String.format("写入%d条耗时：%d ms, 平均每秒写入：%f",
                        total, endTime - startTime , ((float)total/(endTime-startTime))*1000));
                break;
            }
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
