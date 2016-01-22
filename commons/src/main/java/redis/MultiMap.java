/** 
 * File: MultiMap.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package redis;

import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import redis.clients.jedis.Jedis;

/**
 * Description: 
 * Author: Sachiel 
 * Date: 2016-1-18 
 */
public class MultiMap {
    @Test
    public void testMultiMap() {
        Jedis jedis = new Jedis("192.168.6.148", 6379);
        Map<String, User> map = Maps.newHashMap();
        map.put("xue", new User("xue", "beijing"));
        map.put("chong", new User("xue", "shanxi"));
        jedis.set("xue", JSON.toJSONString(map));
        System.out.println(jedis.get("xue"));
    }
    
    private class User {
        private String name;

        private String addr;

        public User(String name, String addr) {
            this.name = name;
            this.addr = addr;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }
    }
}
