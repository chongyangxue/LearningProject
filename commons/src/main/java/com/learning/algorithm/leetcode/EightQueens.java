package com.learning.algorithm.leetcode;

import org.junit.Test;

/**
 * 回溯算法经典题目：八皇后问题
 * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
 * 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 * <p>
 * 矩阵的数学现象：
 * 对角线：    行号 - 列号 = 常数
 * 反对角线：  行号 + 列号 = 常数
 *
 * @author xuechongyang
 */
public class EightQueens {

    @Test
    public void test() {
        int n = 4;
        int[][] array = new int[n][n];
        findPos(array, 0);
    }

    public void findPos(int[][] array, int row) {
        if (row > array.length - 1) {
            printResult(array);
            return;
        }
        for (int col = 0; col < array.length; col++) {
            if (isNotUnderAttack2(array, row, col)) {
                array[row][col] = 1;
                findPos(array, row + 1);
                array[row][col] = 0;
            }
        }
    }

    public void printResult(int[][] array) {
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array.length; col++) {
                if (array[row][col] == 1) {
                    System.out.print("Q\t");
                } else {
                    System.out.print(".\t");
                }
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    public boolean isNotUnderAttack(int[][] array, int row, int col) {
//        int res = rows[col] + hills[row - col + 2 * n] + dales[row + col];
//        return (res == 0) ? true : false;
        return false;
    }

    /**
     * 矩阵的数学现象：
     * 对角线：    行号 - 列号 = 常数
     * 反对角线：  行号 + 列号 = 常数
     */
    private boolean isNotUnderAttack2(int[][] array, int checkRow, int checkCol) {
        for (int col = 0; col < array[0].length; col++) {
            if (array[checkRow][col] == 1) {
                return false;
            }
        }
        for (int row = 0; row < array.length; row++) {
            if (array[row][checkCol] == 1) {
                return false;
            }
        }
        //判断对角线
        int distance = checkRow - checkCol;
        int antiDistance = checkRow + checkCol;
        for (int row = 0; row < array.length; row++) {
            //对角线
            int col = row - distance;
            if (col > 0 && col < array[0].length && array[row][col] == 1) {
                return false;
            }
            //反对角线
            int antiCol = antiDistance - row;
            if (antiCol > 0 && antiCol < array[0].length && array[row][antiCol] == 1) {
                return false;
            }
        }
        return true;
    }

}
