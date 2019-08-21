package com.learning.algorithm;

/**
 * 题目描述：输入两个整数n和m，从数列1，2，3.......n 中随意取几个数，这里同一个数不能重复取，使其和等于m，
 * 要求将其中所有的可能组合列出来。
 *
 * @author Sachiel
 */
public class AllSumArray {

    public static void printArray(int n, int sum) {
        if (sum < 1 || n < 1) {
            return;
        }
        if (n > sum) {
            n = sum;
        }
        int[] array = new int[n];
        int i = 0;
        while (i < n) {
            if (i + n > sum) {
                n--;
            } else if (i + n < sum) {
                i++;
            } else if (i + n == sum && array[i] == 0) {
                array[i] = 1;
                System.out.println(i + "," + n);
                i = 1; n--;
            }
        }
    }

    public static void main(String[] args) {
        printArray(10, 12);
    }
}
