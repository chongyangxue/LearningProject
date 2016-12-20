/** 
 * File: ConditionAndReentraintLock.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition的执行方式，是当在线程1中调用await方法后，线程1将释放锁，并且将自己沉睡，等待唤醒，
 * 线程2获取到锁后，开始做事，完毕后，调用Condition的signal方法，唤醒线程1，线程1恢复执行。
 */
public class ConditionAndReentrantLock {
    
    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("我要等一个信号 " + Thread.currentThread().getName());
                    condition.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("拿到一个信号！" + Thread.currentThread().getName());
                lock.unlock();
            }
        });
        thread.start();
        
        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                lock.lock();
                System.out.println("我拿到锁了 " + Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                condition.signalAll();
                System.out.println("我发了一个信号 " + Thread.currentThread().getName());
                lock.unlock();
            }
        });
        thread1.start();
    }
    
}
