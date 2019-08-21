package com.learning.algorithm.dp;

import org.junit.Test;

/**
 * 最短路径规划
 * 给定一个矩阵m，从左上角开始每次只能向右走或者向下走，最后达到右下角的位置，路径中所有数字累加起来就是路径和，返回所有路径的最小路径和。
 * 如果给定的m如下，那么路径 1,3,1,0,6,1,0 就是最小路径和，返回12.
 * <p>
 * 1 3 5 9
 * 8 1 3 4
 * 5 0 6 1
 * 8 8 4 0
 *
 * @author xuechongyang
 */
public class ShortPath {


    public int findShortPath(int[][] m) {
        int[][] dp = new int[m.length][m[0].length];

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = m[i][j];
                } else if (i == 0 && j != 0) {
                    dp[i][j] = dp[i][j - 1] + m[0][j];
                } else if (i != 0 && j == 0) {
                    dp[i][j] = dp[i - 1][j] + m[i][0];
                } else {
                    dp[i][j] = m[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
                }
                System.out.print(dp[i][j] + "\t");
            }
            System.out.println();
        }
        return dp[m.length - 1][m[0].length - 1];
    }

    @Test
    public void test() {
        int[][] m = {
                {1, 3, 5, 9},
                {8, 1, 3, 4},
                {5, 0, 6, 1},
                {8, 8, 4, 0}};

        System.out.println(findShortPath(m));
    }
}
