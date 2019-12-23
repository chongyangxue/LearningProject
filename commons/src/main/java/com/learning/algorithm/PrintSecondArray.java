package com.learning.algorithm;

import org.junit.Test;

/**
 * @author xuechongyang
 *         二维数组打印
 *         有一个二维数组(n*n),写程序实现从右上角到左下角沿主对角线方向打印。
 *         例如：
 *         输入：[[1,2,3,4],[5,6,7,8],[9,10,11,12],[13,14,15,16]]
 *         输出：[4,3,8,2,7,12,1,6,11,16,5,10,15,9,14,13]
 */
public class PrintSecondArray {

    public int[] printTest(int[][] array) {
        int row = array.length;
        int col = array[0].length;

        int[] result = new int[row * col];
        int index = 0;

        for (int j = col - 1; j >= 0; j--) {
            for (int i = 0, k = j; k < col && i < row; ) {
                result[index++] = array[i++][k++];
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 0, k = i; k < row; ) {
                result[index++] = array[k++][j++];
            }
        }
        return result;
    }

    @Test
    public void test() {
        int[][] array1 = {
                {1,  2,   3,   4},
                {5,  6,   7,   8},
                {9,  10,  11,  12},
                {13, 14,  15,  16}};

        int[][] array2 = {
                {1,  2,   3,   4,  17},
                {5,  6,   7,   8,  18},
                {9,  10,  11,  12, 19},
                {13, 14,  15,  16, 20}
        };

        int [] result = printTest(array2);
        for (int aResult : result) {
            System.out.print(aResult + " ");
        }
    }

}
