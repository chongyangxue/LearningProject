package com.learning.concurrent;

import java.util.Random;

public class ThreadLocalTest {

    private static ThreadLocal<Integer> indexName = new ThreadLocal<>();

    public void setThreadLocal(int data) {
        System.out.println(Thread.currentThread().getName() + " has put data :" + data);
        indexName.set(data);
    }

    public void getThreadLocal() {
        int data = indexName.get();
        System.out.println(Thread.currentThread().getName() + " get data :" + data);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                int data = new Random().nextInt();
                ThreadLocalTest test1 = new ThreadLocalTest();
                test1.setThreadLocal(data);
                ThreadLocalTest test2 = new ThreadLocalTest();
                test1.getThreadLocal();
                test2.getThreadLocal();
            }).start();
        }
    }

}
