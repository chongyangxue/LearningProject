package com.learning.algorithm;

import org.junit.Test;

/**
 * 问题：就是一个排序好的数组 选择一个点 前后旋转一下 然后给定一个数 让返回这个数所在下标
 * @author xuechongyang
 */
public class ReverseArrayFindK {

    /**
     * 就是一个排序好的数组 选择一个点 前后旋转一下 然后给定一个数 让返回这个数所在下标
     */
    private int findK(int[] array, int moved, int k) {
        int start = moved + 1, end = moved;
        int length = 0, mid;
        while (length >= 0) {
            if (start > end) {
                length = (array.length - start + end) / 2;
            } else {
                length = end -start;
                length = length >= 2 ? length / 2 : length;
            }
            mid = start + length;
            if (mid > array.length - 1) {
                mid = mid - array.length - 1;
            }

            System.out.println(String.format("start=%d, end=%d, mid=%d, length=%d", start, end, mid, length));
            if (array[mid] == k) {
                return mid;
            } else if (array[mid] < k) {
                start = start + length;
                if (start > array.length - 1) {
                    start = start - array.length - 1;
                }
            } else {
                end = end - length;
                if (end < 0) {
                    end = end + array.length;
                }
            }
        }
        return -1;
    }

    @Test
    public void testFindK() {
        int[] reverseArray = new int[]{14, 15, 16, 17, 18, 1, 2, 3, 4, 5, 6};
        System.out.println("The k number is :" + findK(reverseArray, 4, 17));
    }
}
