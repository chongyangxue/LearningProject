package com.learning.algorithm;

/**
 * 在两个长度分别为 m 和 n 的有序数组中找到第 k 大的数
 * <p>
 * Created by xuechongyang on 17/3/3.
 */
public class TwoArrayFindK {

    public static int find(int[] a, int[] b, int k) {
        k = k / 2;
        int indexA = k - 1;
        int indexB = k - 1;
        if (indexA >= a.length) {
            indexA = a.length - 1;
            indexB = k - indexA;
        }
        if (indexB >= b.length) {
            indexB = b.length - 1;
            indexA = k - indexB;
        }
        while (k != 0) {
            if (a[indexA] < b[indexB]) {
                k = k / 2;
                indexA = indexA + k;
                indexB = indexB - k;
                if (indexB <= 0) {
                    return Math.min(a[indexA + 1], b[indexB]);
                }
            } else if (a[indexA] > b[indexB]) {
                k = k / 2;
                indexA = indexA - k;
                indexB = indexB + k;
                if (indexA <= 0) {
                    return Math.min(b[indexB + 1], a[indexA]);
                }
            } else {
                return a[indexA];
            }
            if (indexA >= a.length) {
                k = k / 2;
                indexA = indexA - k;
                indexB = indexB + k;
            }
            if (indexB >= a.length) {
                k = k / 2;
                indexA = indexA + k;
                indexB = indexB - k;
            }
        }
        return a[indexA];
    }

    //寻找两个有序数组的中位数
    public static int findMid(int[] a, int[] b) {
        int k = (a.length + b.length) / 2;
        return find(a, b, k);
    }

    public static void main(String[] args) {
        int[] a = new int[]{1, 3};
        int[] b = new int[]{2};
        System.out.println("The k number is :" + findMid(a, b));
    }


}
