/** 
 * File: Performance.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * Description: 
 * Author: Sachiel 
 * Date: 2016-1-18 
 */
public class Performance {
    public static void testPerformance() {
        Jedis jedis = new Jedis("192.168.6.148", 6379);
        long startTime=System.currentTimeMillis();   //获取开始时间  
        Pipeline p = jedis.pipelined();
        System.out.println("开始写入测试");
        for(int i = 0; i < 1000000; i++){  
            p.set("foo"+i, "foo"+i);
        } 
        p.sync();
        long endTime=System.currentTimeMillis(); //获取结束时间  
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");    
        System.out.println("每毫秒写入:"+10000/(endTime-startTime)+"条。");  
        System.out.println("每秒写入:"+(10000/(endTime-startTime))*1000+"条。"); 
        
        for(int i = 0; i < 1000000; i++){  
            p.del("foo"+i);
        } 
    }
}
