package com.learning.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 螺旋打印二维数组
 * @author xuechongyang
 */
public class PrintCircleArray {

    public List<Integer> print(int[][] array) {
        List<Integer> result = new ArrayList<>();
        if (array.length <= 0 || array[0].length <= 0) {
            return result;
        }
        int row0 = 0;
        int col0 = 0;
        int row1 = array.length - 1;
        int col1 = array[0].length - 1;

        while (row0 <= row1 && col0 <= col1) {
            for (int i = col0; i <= col1; i++) {
                result.add(array[row0][i]);
            }
            for (int i = row0 + 1; i <= row1; i++) {
                result.add(array[i][col1]);
            }
            for (int i = col1 - 1; i >= col0 && row0 < row1; i--) {
                result.add(array[row1][i]);
            }
            for (int i = row1 - 1; i >= row0 + 1 && col0 < col1; i--) {
                result.add(array[i][col0]);
            }
            row0++;
            col0++;
            row1--;
            col1--;
        }
        return result;
    }

    @Test
    public void test() {
        int[][] array = {
                {1, 2, 3, 4},
                {12, 13, 14, 5},
                {11, 16, 15, 6},
                {10, 9, 8, 7},
        };
        int[][] array2 = {
                {1,  2,  3,  4},
                {10, 11, 12, 5},
                {9,  8,  7,  6},
        };
        int[][] array3 = {
                {1,  2, 3},
                {10, 11, 4},
                {9,  12, 5},
                {8,  7, 6}
        };
        int[][] array4 = {
                {1},
                {2},
                {3}
        };
        System.out.println(print(array));
        System.out.println(print(array2));
        System.out.println(print(array3));
        System.out.println(print(array4));
    }
}
