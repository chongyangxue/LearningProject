package com.learning.algorithm;

import org.junit.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用三个线程有序打印数组
 * @author xuechongyang
 */
public class ThreadsPrintArray {

    public void threadsPrint(int[] array) throws InterruptedException {
        AtomicInteger index = new AtomicInteger(0);
        Semaphore semaphoreA = new Semaphore(0);
        Semaphore semaphoreB = new Semaphore(0);
        Semaphore semaphoreC = new Semaphore(1);
        new Thread(() -> {
            try {
                while (index.get() < array.length - 1) {
                    semaphoreC.acquire();
                    if (index.addAndGet(1) < array.length) {
                        System.out.println("Thread A print: " + array[index.get()]);
                    }
                    semaphoreA.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                while (index.get() < array.length - 1) {
                    semaphoreA.acquire();
                    if (index.addAndGet(1) < array.length) {
                        System.out.println("Thread B print: " + array[index.get()]);
                    }
                    semaphoreB.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                while (index.get() < array.length - 1) {
                    semaphoreB.acquire();
                    if (index.addAndGet(1) < array.length) {
                        System.out.println("Thread C print: " + array[index.get()]);
                    }
                    semaphoreC.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        Thread.currentThread().join();
    }

    @Test
    public void test() throws InterruptedException {
        int[] array = new int[100];
        for (int i = 0; i < 100; i++) {
            array[i] = i;
        }
        threadsPrint(array);
    }
}
