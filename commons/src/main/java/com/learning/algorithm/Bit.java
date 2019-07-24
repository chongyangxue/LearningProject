package com.learning.algorithm;

import org.junit.Test;

/**
 * 位操作
 * @author xuechongyang
 */
public class Bit {

    /**
     * 找出一个整数二进制中1的个数
     */
    @Test
    public void findNumberOfInt() {
        int val = -4;
        int n = 0;
        while (val != 0) {
            val = val & (val - 1);
            n++;
        }
        System.out.println(n);
    }

    /**
     * 找出一个整数二进制中1的个数，通过移位操作
     */
    @Test
    public void findNumberOfInt1() {
        int val = -7;
        int n = 0;
        while (val != 0) {
            if ((val & 1) == 1) {n++;}
            val = val >> 1;
        }
        System.out.println(n);
        System.out.println(val >>> 1);
    }

}
