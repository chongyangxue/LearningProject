/** 
 * File: ParkTest.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * Description: 
 * Author: Sachiel 
 * Date: 2016年5月19日 
 */
public class ParkTest {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " parked");
                LockSupport.park();
                System.out.println(Thread.currentThread().getName() + " awaked");
            }
            
        });
        thread.start();
        thread.interrupt();
        //LockSupport.unpark(thread);
    }
}
