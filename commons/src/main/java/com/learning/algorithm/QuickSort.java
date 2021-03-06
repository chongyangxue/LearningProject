package com.learning.algorithm;

public class QuickSort {
    public static int[] swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        return array;
    }

    public static int[] quickSort(int[] array, int start, int end) {

        if (start < end) {
            int mid = array[start];
            int i = start;
            int j = end;
            while (i < j) {
                while (i < end && array[i] <= mid) {
                    i++;
                }
                while (j > start && array[j] >= mid) {
                    j--;
                }
                if (i < j) {
                    swap(array, i, j);
                }
            }
            for (int k : array) {
                if (k == mid) {
                    System.out.print("[" + k + "] ");
                } else {
                    System.out.print(k + " ");
                }
            }
            System.out.println("i=" + i + ", j=" + j);
            //把头元素换到中间，这一步很关键;因为此时i肯定大于mid，j小于mid
            array = swap(array, start, j);

            quickSort(array, start, j - 1);
            quickSort(array, j + 1, end);
            return array;
        }
        return null;
    }


    public static void main(String[] args) {
//        int[] array = new int[]{3, 1, 7, 4, 9, 2, 6, 5, 0};
//        int[] array = {4, 3, 7, 8, 5, 4, 2, 5, 65};
        int [] array = {5, 4, 8};
        array = quickSort(array, 0, array.length - 1);
        for (int i : array) {
            System.out.print(i + " ");
        }
    }
}