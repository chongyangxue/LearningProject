/**
 * File: ConditionAndReentraintLock.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.concurrent;

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
