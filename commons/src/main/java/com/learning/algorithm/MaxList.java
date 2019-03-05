package com.learning.algorithm;

/**
 * 求最大递增子序列的长度
 *
 * @author Sachiel
 */
public class MaxList {

    private static int[] MaxSubList(int[] array) {
        int[] list = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            list[i] = 1;
            for (int j = 0; j < i; j++) {
                if (array[j] < array[i] && list[j] + 1 > list[i])
                    list[i] = list[j] + 1;
            }
        }
        return list;
    }

    public static void main(String[] args) {
        int[] array = new int[]{1, -2, -3, 4, 5, -3, -1, 2};
        int[] list = MaxSubList(array);
        for (int i : list)
            System.out.print(i + "  ");
    }
}
