package com.learning.algorithm.leetcode;

import org.junit.Test;

/**
 * 使用快排找数组的中位数
 *
 * @author xuechongyang
 */
public class QuickSortFindMedian {

    @Test
    public void test() {
        //[1, 2, 3, 4, 4, 6, 7, 8, 9, 10, 65]
        int[] array = {4, 3, 7, 8, 6, 4, 2, 1, 65, 9, 10};
        System.out.println(findMedian(array));
    }

    public int findMedian(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int targetIndex = array.length % 2 == 0 ? (array.length / 2 - 1) : (array.length / 2);

        int start = 0;
        int end = array.length - 1;
        int median = start;
        while (median != targetIndex) {
            median = quickSort(array, start, end);
            if (median < targetIndex) {
                start = median + 1;
            } else {
                end = median - 1;
            }
        }
        return array[median];
    }

    private int quickSort(int[] array, int start, int end) {
        int i = start;
        int j = end;
        while (i < j) {
            while (i < end && array[i] <= array[start]) {
                i++;
            }
            while (j > start && array[j] >= array[start]) {
                j--;
            }
            if (i < j) {
                array = swap(array, i, j);
            }
        }
        swap(array, start, j);
        return j;
    }

    private int[] swap(int[] array, int start, int end) {
        int tmp = array[start];
        array[start] = array[end];
        array[end] = tmp;
        return array;
    }
}
