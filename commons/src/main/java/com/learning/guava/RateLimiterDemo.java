package com.learning.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xuechongyang
 */
public class RateLimiterDemo {

    /**
     * 每秒不超过50个请求
     */
    public static final RateLimiter limiter = RateLimiter.create(50);


    @Test
    public void test() throws InterruptedException {
        ExecutorService threadPool = new ThreadPoolExecutor(4, 8, 3, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),
                new ThreadPoolExecutor.CallerRunsPolicy());

        AtomicInteger passCount = new AtomicInteger(0);
        AtomicInteger rejectCount = new AtomicInteger(0);

        for (int i = 0; i < 1000; i++) {
            threadPool.execute(() -> {
                if (limiter.tryAcquire()) {
                    System.out.println("流控通过，执行逻辑: " + passCount.addAndGet(1));
                } else {
                    System.out.println("流控拒绝，放弃执行: " + rejectCount.addAndGet(1));
                }
            });
            Thread.sleep(10);
        }
        Thread.sleep(1000);
        System.out.println("通过：" + passCount.get() + ", 拒绝：" + rejectCount.get());
    }

}
