package com.learning.algorithm.leetcode.dp;

import org.junit.Test;

/**
 * 给定数组arr，返回arr的最长递增子序列的长度
 * 比如arr=[2,1,5,3,6,4,8,9,7]，最长递增子序列为[1,3,4,8,9]返回其长度为5.
 *
 * @author xuechongyang
 */
public class LongestSubArray {

    private int findSubArrayLength(int[] array) {
        int[] dp = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            int max = 1;
            int last = array[i];
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] > last) {
                    max++;
                    last = array[j];
                }
            }
            dp[i] = max;
        }
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            if (dp[i] > result) {
                result = dp[i];
            }
        }
        return result;
    }

    /**
     * 最长连续子序列
     */
    private int findSubArrayLength2(int[] array) {
        if (array == null || array.length <= 0) {
            return 0;
        }
        int[] dp = new int[array.length];
        int max = 0;
        dp[0] = 1;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[i - 1]) {
                dp[i] = dp[i - 1] + 1;
                if (dp[i] > max) {
                    max = dp[i];
                }
            } else {
                dp[i] = 1;
            }
        }
        return max;
    }

    /**
     * NlogN时间复杂度的算法
     * 加一个数组B，存储的对应长度递增子串的最小末尾
     */
    private int findSubArrayLength3(int[] array) {
        int len = 0;
        int B[] = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > B[len]) {
                B[++len] = array[i];
            } else {
                int index = biSearch(B, len, array[i]);
                B[index] = array[i];
            }
        }
        return len;
    }

    /**
     * 二分查找插入位置
     */
    private int biSearch(int[] array, int len, int value) {
        int start = 0;
        int end = len;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (array[mid] < value) {
                start = mid + 1;
            } else if (array[mid] > value) {
                end = mid - 1;
            } else {
                return mid;
            }
        }
        return start;
    }

    @Test
    public void test() {
//        int[] array = {2, 1, 5, 3, 6, 4, 1, 2, 3};
        int[] array = {2, 1, 5, 3, 6, 4, 8, 9, 7, 10};
        System.out.println(findSubArrayLength(array));
        System.out.println(findSubArrayLength3(array));
    }
}
