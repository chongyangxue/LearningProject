package com.learning.algorithm;

public class ZheBanChaZhao {
    public static int find(int[] array, int k) {
        int mid = array.length / 2;
        int min = 0;
        int max = array.length - 1;
        while (min <= max) {
            System.out.println(min + " ----- " + max);
            mid = (min + max) / 2;
            if (array[mid] == k) {
                return mid;
            } else if (array[mid] < k) {
                min = mid;
            } else {
                max = mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(find(array, 1));
    }
}
