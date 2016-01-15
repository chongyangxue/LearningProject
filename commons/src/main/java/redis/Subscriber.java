/** 
 * File: Subscriber.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package redis;

import redis.clients.jedis.JedisPubSub;

/**
 * Description: 
 * Author: Sachiel 
 * Date: 2016-1-15 
 */
public class Subscriber extends JedisPubSub{
    @Override
    public void onMessage(String channel, String message) {
        System.out.println("Message received. Channel: " + channel + ", Msg: " + message);
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
