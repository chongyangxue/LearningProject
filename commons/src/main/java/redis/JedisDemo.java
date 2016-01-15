package redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

public class JedisDemo {

    public void usage() {
        //连接redis服务  
        Jedis jedis = new Jedis("10.16.24.2", 1039);

        //密码验证-如果你没有设置redis密码可不验证即可使用相关命令  
        jedis.auth("4a3b23ad5e8a30dfad971d3a3d473799");

        //简单的key-value 存储  
        jedis.set("redis", "myredis");
        System.out.println(jedis.get("redis"));

        //在原有值得基础上添加,如若之前没有该key，则导入该key  
        //之前已经设定了redis对应"myredis",此句执行便会使redis对应"myredisyourredis"  
        jedis.append("redis", "yourredis");
        jedis.append("content", "rabbit");

        //mset 是设置多个key-value值   参数（key1,value1,key2,value2,...,keyn,valuen）  
        //mget 是获取多个key所对应的value值  参数（key1,key2,key3,...,keyn）  返回的是个list  
        jedis.mset("name1", "yangw", "name2", "demon", "name3", "elena");
        System.out.println(jedis.mget("name1", "name2", "name3"));

        //map  
        Map<String, String> user = new HashMap<String, String>();
        user.put("name", "cd");
        user.put("password", "123456");
        //map存入redis  
        jedis.hmset("user", user);
        //mapkey个数  
        System.out.println(String.format("len:%d", jedis.hlen("user")));
        //map中的所有键值  
        System.out.println(String.format("keys: %s", jedis.hkeys("user")));
        //map中的所有value  
        System.out.println(String.format("values: %s", jedis.hvals("user")));
        //取出map中的name字段值  
        List<String> rsmap = jedis.hmget("user", "name", "password");
        System.out.println(rsmap);
        //删除map中的某一个键值 password  
        jedis.hdel("user", "password");
        System.out.println(jedis.hmget("user", "name", "password"));

        //list  
        jedis.del("listDemo");
        System.out.println(jedis.lrange("listDemo", 0, -1));
        jedis.lpush("listDemo", "A");
        jedis.lpush("listDemo", "B");
        jedis.lpush("listDemo", "C");
        System.out.println(jedis.lrange("listDemo", 0, -1));
        System.out.println(jedis.lrange("listDemo", 0, 1));

        //set  
        jedis.sadd("sname", "wobby");
        jedis.sadd("sname", "kings");
        jedis.sadd("sname", "demon");
        System.out.println(String.format("set num: %d", jedis.scard("sname")));
        System.out.println(String.format("all members: %s", jedis.smembers("sname")));
        System.out.println(String.format("is member: %B", jedis.sismember("sname", "wobby")));
        System.out.println(String.format("rand member: %s", jedis.srandmember("sname")));
        //删除一个对象  
        jedis.srem("sname", "demon");
        System.out.println(String.format("all members: %s", jedis.smembers("sname")));
    }

    /**
     * 用redis实现一个分布式锁
     * @author：Sachiel
     */
    public static void DistributedLock() {
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
    
    public static void pubSub() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(poolConfig, "192.168.6.148", 6379, 0);
        final Jedis subscriberJedis = jedisPool.getResource();
        final Subscriber subscriber = new Subscriber();
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
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
        
        final Jedis publisherJedis = jedisPool.getResource();
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 20; i++){  
                    //publisherJedis.publish("channel", "pub_" + i);
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                } 
            }
        });
    }

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

    public static void main(String[] args) throws InterruptedException {
        //testPerformance();
        
        pubSub();
        
        /*ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 4; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    DistributedLock();
                }
            });
        }*/
    }
}
