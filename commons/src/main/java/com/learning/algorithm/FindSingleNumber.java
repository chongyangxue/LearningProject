package com.learning.algorithm;

import org.junit.Assert;
import org.junit.Test;

import java.util.BitSet;

/**
 * 找出一个数组中只出现一次的数，其他数都是两次
 *
 * @author Sachiel
 */
public class FindSingleNumber {


    /**
     * 找出一个数组中只出现一次的数，其他数都是两次
     */
    public int getSingle(int[] array) {
        int result = 0;
        for (int i = 0 ; i < array.length; i++) {
            result ^= array[i];
        }
        return result;
    }

    /**
     * 找出一个数组中只出现2次的数，其他数都是3次
     * 思路：32位整数，对所有元素每位加和对3取余，得出该为是否为1
     */
    public int getTwice(int[] array) {
        BitSet bitSet = new BitSet(32);
        for (int index = 0 ; index < 32; index++) {
            int sum = 0;
            for (int i : array) {
                sum += (i >> index & 1);
            }
            if (sum % 3 != 0) {
                bitSet.set(index);
            }
        }
        int result = 0;
        for (int index = 0; index < 32; index++) {
            if (bitSet.get(index)) {
                result += Math.pow(2, index);
            }
        }
        return result;
    }

    @Test
    public void testSingle () {
        int[] array = new int[]{1, 1, 2, 2, 9, 3, 4, 4, 3};
        int number = getSingle(array);
        Assert.assertEquals(number, 9);
    }

    @Test
    public void testTwice () {
        int[] array1 = new int[]{1, 1, 1, 2, 2, 2, 4, 4};
        int[] array2 = new int[]{1, 1, 1, 2, 2, 2, 3, 3};
        int[] array3 = new int[]{10, 10, 10, 299, 299, 299, 23, 23};
        Assert.assertEquals(4, getTwice(array1));
        Assert.assertEquals(3, getTwice(array2));
        Assert.assertEquals(23, getTwice(array3));
    }

    @Test
    public void testDif() {
        int a = 2;
        int b = 3;
        Assert.assertEquals(1, a^b);
        Assert.assertEquals(1, 2^3^4^4^3^2^1);
        System.out.println(1 | 1 | 2 | 2);
    }
}
