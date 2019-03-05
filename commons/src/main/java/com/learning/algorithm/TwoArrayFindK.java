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
                if (indexB == 0) {
                    return Math.min(a[indexA + 1], b[indexB]);
                }
            } else if (a[indexA] > b[indexB]) {
                k = k / 2;
                indexA = indexA - k;
                indexB = indexB + k;
                if (indexA == 0)
                    return Math.min(b[indexB + 1], a[indexA]);
            } else {
                return a[indexA];
            }

            for (int i = 0; i <= indexA; i++) {
                System.out.print(a[i] + " ");
            }
            System.out.println();
            for (int i = 0; i <= indexB; i++) {
                System.out.print(b[i] + " ");
            }
            System.out.println();
            System.out.println("------------------");
        }
        return a[indexA];
    }

    private static  int divideK(int k) {
        if (k % 2 != 0) {
            k = k / 2;
            return k + 1;
        } else {
            return k / 2;
        }
    }

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 5, 7};
        int[] b = new int[]{4, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("The k number is :" + find(a, b, 10));
    }


}
