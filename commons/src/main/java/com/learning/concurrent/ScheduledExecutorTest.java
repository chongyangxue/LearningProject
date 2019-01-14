package com.learning.concurrent;

import org.junit.Test;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xuechongyang
 */
public class ScheduledExecutorTest {

    /**
     * 延时任务
     */
    @Test
    public void testSchedule() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        for (int i = 0; i < 5; i++) {
            scheduledExecutorService.schedule(() -> System.out.println("延迟执行任务"), i + 1, TimeUnit.SECONDS);
        }

        scheduledExecutorService.awaitTermination(10, TimeUnit.SECONDS);
    }

    /**
     * 循环任务，按照上一次任务的发起时间计算下一次任务的开始时间
     */
    @Test
    public void testScheduleAtFixRate() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> System.out.println("延迟执行"), 1, 1, TimeUnit.SECONDS);

        scheduledExecutorService.awaitTermination(10, TimeUnit.SECONDS);
    }

    /**
     * 循环任务，按照上一次任务的结束时间计算下一次任务的开始时间
     */
    @Test
    public void testScheduleAtFixDelay() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleWithFixedDelay(() -> System.out.println("延迟执行"), 1, 1, TimeUnit.SECONDS);

        scheduledExecutorService.awaitTermination(10, TimeUnit.SECONDS);
    }
}
