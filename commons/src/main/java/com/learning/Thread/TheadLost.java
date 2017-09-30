package com.learning.Thread;

import java.util.concurrent.*;

/**
 * Created by xuechongyang on 17/8/23.
 *
 * 重现线程泄露的情况
 */
public class TheadLost {

    /*private static ExecutorService threadPool = new ThreadPoolExecutor(4, 8, 5, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(1),
            new NamedThreadFactory("TestThreadLost"),
            new ThreadPoolExecutor.AbortPolicy());*/

    private static ExecutorService threadPool = Executors.newFixedThreadPool(1);

    private static void process() {
        threadPool.execute(() -> {
            StringBuilder sb = null;
            System.out.println(sb.toString());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor scheduleService = new ScheduledThreadPoolExecutor(1);
        scheduleService.scheduleAtFixedRate(TheadLost::process, 1, 1, TimeUnit.SECONDS);
    }

}
