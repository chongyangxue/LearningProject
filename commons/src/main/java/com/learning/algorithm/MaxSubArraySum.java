package com.learning.algorithm;

/**
 * 求数组的连续子数组的和的最大值
 *
 * @author Sachiel
 */
public class MaxSubArraySum {
    public static int MaxSum1(int[] array) {
        int max = -1;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i; j < array.length - 1; j++) {
                int sum = 0;
                for (int k = i; k < j; k++) {
                    sum += array[k];
                }
                if (max < sum) {
                    max = sum;
                }
            }
        }
        return max;
    }

    public static int MaxSum2(int[] array) {
        int max = -1;
        for (int i = 0; i < array.length - 1; i++) {
            int sum = 0;
            for (int j = i; j < array.length - 1; j++) {
                sum += array[j];
                if (max < sum)
                    max = sum;
            }
        }
        return max;
    }

    /**
     * nStart表示从第i位开始到尾部的和的最大值, nAll表示从第i位开始最大的子数组和
     *
     * @param array
     * @return
     */
    public static int MaxSum3(int[] array) {
        int n = array.length;
        int nStart = array[n - 1];
        int max = array[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            nStart = max(array[i], array[i] + nStart);
            if (nStart > max)
                max = nStart;
            System.out.println("nStart = " + nStart + ", nAll = " + max);
        }
        return max;
    }

    /**
     * nStart表示从第i位开始到尾部的和的最大值, nAll表示从第i位开始最大的子数组和
     *
     * @param array
     * @return
     */
    public static int MaxSum4(int[] array) {
        int n = array.length;
        int nStart = array[n - 1];
        int nAll = array[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            if (nStart < 0)
                nStart = 0;
            nStart += array[i];
            if (nStart > nAll)
                nAll = nStart;
            System.out.println("nStart = " + nStart + ", nAll = " + nAll);
        }
        return nAll;
    }

    /**
     * nStart表示从第i位开始到尾部的和的最大值, nAll表示从第i位开始最大的子数组和
     *
     * @param array
     * @return
     */
    public static int MaxSum5(int[] array) {
        int n = array.length;
        int nStart = array[0];
        int max = array[0];

        for (int i = 1; i < n - 1; i++) {
            if (nStart < 0)
                nStart = 0;
            nStart += array[i];
            if (nStart > max)
                max = nStart;
            System.out.println("nStart = " + nStart + ", nAll = " + max);
        }
        return max;
    }

    public static int max(int x, int y) {
        return (x > y) ? x : y;
    }

    public static void main(String[] args) {
        int[] array = new int[]{1, -2, -3, 4, 5, -3, -1, 2};
        System.out.println("MaxSum1: " + MaxSum1(array));
        System.out.println("MaxSum2: " + MaxSum2(array));
        System.out.println("MaxSum3: " + MaxSum3(array));
        System.out.println("MaxSum4: " + MaxSum4(array));
        System.out.println("MaxSum5: " + MaxSum5(array));
    }
}
