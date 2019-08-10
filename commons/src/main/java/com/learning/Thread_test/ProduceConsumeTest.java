package com.learning.Thread_test;

import org.junit.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xuechongyang
 */
public class ProduceConsumeTest {

    @Test
    public void test() throws InterruptedException {
        AtomicInteger count = new AtomicInteger(100);
        Semaphore semaphoreA = new Semaphore(0);
        Semaphore semaphoreB = new Semaphore(1);
        Thread threadA = new Thread(() -> {
            try {
                while (count.get() > 0) {
                    semaphoreB.acquire();
                    System.out.println(Thread.currentThread().getName() + ": A" + ", " + count.get());
                    count.decrementAndGet();
                    semaphoreA.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread threadB = new Thread(() -> {
            try {
                while (count.get() > 0) {
                    semaphoreA.acquire();
                    System.out.println(Thread.currentThread().getName() + ": B" + ", " + count.get());
                    count.decrementAndGet();
                    semaphoreB.release();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
        System.out.println("执行完毕");
    }

    @Test
    public void testB() throws InterruptedException {
        AtomicInteger count = new AtomicInteger(100);
        ReentrantLock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Thread threadA = new Thread(() -> {
            try {
                lock.lock();
                while (count.get() > 0) {
                    conditionB.await();
                    System.out.println(Thread.currentThread().getName() + ": A" + ", " + count.get());
                    count.decrementAndGet();
                    conditionA.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread threadB = new Thread(() -> {
            try {
                lock.lock();
                while (count.get() > 0) {
                    System.out.println(Thread.currentThread().getName() + ": B" + ", " + count.get());
                    count.decrementAndGet();
                    conditionB.signal();
                    conditionA.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();
        System.out.println("执行完毕");
    }
}
