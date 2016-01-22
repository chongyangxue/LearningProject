/** 
 * File: DistributedLock.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package redis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;

/**
 * Description: 
 * Author: Sachiel 
 * Date: 2016-1-18 
 */
public class DistributedLock {
    /**
     * 用redis实现一个分布式锁
     * @author：Sachiel
     */
    public static void lock() {
        Jedis jedis = new Jedis("192.168.6.148", 6379);
        String threadName = Thread.currentThread().getName();
        Long result = jedis.setnx("lock", threadName);
        if (result.equals(1l)) {
            try {
                jedis.expire("lock", 5);
                System.out.println(threadName + " get lock!");
                Thread.sleep(3000); //do something
                
                //有可能已经超时，锁被别的进程获取了。判断是否是自己的锁
                if(jedis.get("lock").equals(threadName)) {
                    jedis.del("lock");
                }
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void test() {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 4; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    lock();
                }
            });
        }
    }
}
