package com.learning.algorithm.leetcode.dp;

import org.junit.Test;

/**
 * 台阶问题
 * 有n级台阶，一个人每次上一级或者两级，问有多少种走完n级台阶的方法。
 * @author xuechongyang
 */
public class StepProblem {

    private int[] dp = new int[10];

    public int fun(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        if (dp[n - 1] == 0) {
            dp[n - 1] = fun(n - 1);
        }
        if (dp[n - 2] == 0) {
            dp[n - 2] = fun(n - 2);
        }
        return dp[n - 1] + dp[n - 2];
    }

    @Test
    public void test() {
        System.out.println(fun(10));
    }
}
