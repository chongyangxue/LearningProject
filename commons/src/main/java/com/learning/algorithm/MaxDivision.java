package com.learning.algorithm;

import org.junit.Assert;
import org.junit.Test;

import java.util.BitSet;

/**
 * @author xuechongyang
 *         请设计一个复杂度为O(n)的算法，计算一个未排序数组中排序后相邻元素的最大差值。
 *         例如：[9,3,1,10]，返回 6
 *         思路：用位图
 */
public class MaxDivision {

    private int maxDistance(int[] array) {
        BitSet bitSet = new BitSet(Integer.MAX_VALUE);
        for (int i : array) {
            bitSet.set(i);
        }
        int lastIndex = 0;
        int max = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (bitSet.get(i)) {
                if (i - lastIndex > max) {
                    max = i - lastIndex;
                }
                lastIndex = i;
            }
        }
        return max;
    }

    @Test
    public void test() {
        int[] array = new int[]{9, 3, 1, 10};
        Assert.assertEquals(6, maxDistance(array));
    }
}
