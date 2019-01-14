package com.learning.concurrent;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xuechongyang
 *
 * ScheduledThreadPoolExecutor简析
 * 核心实现逻辑在内部的DelayedWorkQueue，是一个以延迟执行时间作为小根堆实现的优先级队列
 *
 * A DelayedWorkQueue is based on a heap-based data structure
 * like those in DelayQueue and PriorityQueue, except that
 * every ScheduledFutureTask also records its index into the
 * heap array. This eliminates the need to find a task upon
 * cancellation, greatly speeding up removal (down from O(n)
 * to O(log n)), and reducing garbage retention that would
 * otherwise occur by waiting for the element to rise to top
 * before clearing. But because the queue may also hold
 * RunnableScheduledFutures that are not ScheduledFutureTasks,
 * we are not guaranteed to have such indices available, in
 * which case we fall back to linear search. (We expect that
 * most tasks will not be decorated, and that the faster cases
 * will be much more common.)
 *
 * All heap operations must record index changes -- mainly
 * within siftUp and siftDown. Upon removal, a task's
 * heapIndex is set to -1. Note that ScheduledFutureTasks can
 * appear at most once in the queue (this need not be true for
 * other kinds of tasks or work queues), so are uniquely
 * identified by heapIndex.
 */
public class ScheduledExecutorTest {

    /**
     * 延时任务
     */
    @Test
    public void testSchedule() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        for (int i = 1; i < 10; i++) {
            scheduledExecutorService.schedule(() -> System.out.println(new Date() + ": 延迟执行任务"), i * 10, TimeUnit.SECONDS);
        }
        scheduledExecutorService.awaitTermination(100, TimeUnit.SECONDS);
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
