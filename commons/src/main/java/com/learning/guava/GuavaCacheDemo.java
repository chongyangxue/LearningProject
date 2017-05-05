package com.learning.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xuechongyang on 17/5/5.
 */
public class GuavaCacheDemo {

    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    private static final CyclicBarrier barrier = new CyclicBarrier(100);

    private static final AtomicInteger atomicCount = new AtomicInteger(0);

    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    System.out.println("loading key:" + key);
                    return key + "1";
                }
            });

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            threadPool.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "第" + atomicCount.getAndAdd(1) + "个线程已就绪");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + "开始查询缓存");
                    String value = localCache.get("key");
                    System.out.println(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
