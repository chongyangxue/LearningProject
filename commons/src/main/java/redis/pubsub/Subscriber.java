/** 
 * File: Subscriber.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package redis.pubsub;

import java.util.concurrent.atomic.AtomicInteger;

import redis.clients.jedis.JedisPubSub;

/**
 * Description: 
 * Author: Sachiel 
 * Date: 2016-1-15 
 */
public class Subscriber extends JedisPubSub{
    
    private static AtomicInteger count = new AtomicInteger(0);
    private long startTime;
    private int calCount = 10000;
    @Override
    public void onMessage(String channel, String message) {
        if (count.get() == 0) {
            startTime = System.currentTimeMillis();   //获取开始时间  
        }
        if(count.addAndGet(1) > calCount) {
            long endTime=System.currentTimeMillis(); //获取结束时间 
            String log = String.format("读取%d条耗时：%d ms, 平均每秒读取：%d",
                    calCount, endTime - startTime , (calCount/(endTime-startTime))*1000);
            System.out.println(log);
            count.set(0);
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {

    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {

    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {

    }
}
