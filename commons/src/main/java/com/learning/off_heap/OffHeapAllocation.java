package com.learning.off_heap;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by xuechongyang on 17/1/12.
 */
public class OffHeapAllocation {
    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
        } catch(Exception e) {
        }
    }

    public static void main(String[] args) {
        while (true) {
            long addr = unsafe.allocateMemory(8 * 1000000000L);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            unsafe.freeMemory(addr);
        }
    }
}
