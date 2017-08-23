package com.learning.Thread;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by xuechongyang on 17/8/23.
 *
 * 重现线程泄露的情况
 */
public class TheadLost {

    private static ExecutorService threadPool = new ThreadPoolExecutor(4, 8, 5, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(3000),
            new NamedThreadFactory("TestThreadLost"),
            new ThreadPoolExecutor.AbortPolicy());

    @Test
    public void test() {
        new Thread(() -> {
            threadPool.execute(() -> {
                while (true) {
                    StringBuilder sb = null;
                    System.out.println(sb.toString());

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }).start();

    }
}
