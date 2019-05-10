package com.learning.algorithm;

import org.junit.Test;

import java.util.BitSet;

/**
 * @author xuechongyang
 *         通过位图，利用1MB内存对1000万个7位数排序
 */
public class BitSort {

    @Test
    public void bitSort() {

        BitSet bitArray = new BitSet(9999999);

        for (int i = 0; i < 1000; i++) {
            int number = (int) ((Math.random() * 9 + 1) * 1000000);
            System.out.println(number);
            bitArray.set(number);
        }

        System.out.println("================================");
        System.out.println("================================");

        for (int i = 0; i < bitArray.size(); i++) {
            if (bitArray.get(i)) {
                System.out.println(i);
            }
        }
    }
}
