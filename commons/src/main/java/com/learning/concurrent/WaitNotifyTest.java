/**
 * File: ConditionAndReentraintLock.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.concurrent;

/**
 * 调用wait方法后，线程是会释放对monitor对象的所有权的。
 * 一个通过wait方法阻塞的线程，必须同时满足以下两个条件才能被真正执行：
        线程需要被唤醒（超时唤醒或调用notify/notifyll）。
 　　　　线程唤醒后需要竞争到锁（monitor）。
 * notify和notifyAll的区别在于前者只能唤醒monitor上的一个线程，对其他线程没有影响，而notifyAll则唤醒所有的线程
 */
public class WaitNotifyTest {

    private boolean isEmpty;

    public synchronized void produce() throws InterruptedException {
        System.out.println("进入produce");
        while (true) {
            if (!isEmpty) {
                System.out.println("等待消费");
                wait();
            }
            isEmpty = false;
            notify();
            Thread.sleep(1000);
        }

    }

    public synchronized void consume() throws InterruptedException {
        System.out.println("进入consume");
        while (true) {
            if (isEmpty) {
                System.out.println("等待生产");
                    wait();
            }
            isEmpty = true;
            notify();
            Thread.sleep(1000);
        }

    }

    public static void main(String[] args) {
        WaitNotifyTest test = new WaitNotifyTest();

        Thread thread1 = new Thread(() -> {
            try {
                test.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            try {
                test.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread2.start();
    }

}
