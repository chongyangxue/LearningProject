package com.learning.algorithm;

/**
 * 把一个数组循环右移k位，要求时间复杂度O(n),且只用两个附加变量
 *
 * @author Sachiel
 */
public class MoveArray {

    public static void main(String[] args) {
        int[] array = new int[]{1, -2, -3, 4, 5, -3, -1, 2};
        for (int i : array) {
            System.out.print(i + "  ");
        }
        System.out.println();
        int[] moveResult = moveK(array, 3);
        for (int i : moveResult) {
            System.out.print(i + "  ");
        }
        System.out.println();
        int[] reverseResult = reverseK(array, 3);
        for (int i : reverseResult) {
            System.out.print(i + "  ");
        }
    }

    /**
     * 数组循环右移K位
     */
    private static int[] moveK(int[] array, int k) {
        k %= array.length;
        array = Reverse(array, 0, array.length - k - 1);
        array = Reverse(array, array.length - k, array.length - 1);
        array = Reverse(array, 0, array.length - 1);
        return array;
    }

    /**
     * 给定一个位置K，前后旋转
     */
    private static int[] reverseK(int[] array, int k) {
        return moveK(array, array.length - k);
    }

    private static int[] Reverse(int[] array, int b, int e) {
        for (; b < e; b++, e--) {
            int temp = array[b];
            array[b] = array[e];
            array[e] = temp;
        }
        return array;
    }


}
