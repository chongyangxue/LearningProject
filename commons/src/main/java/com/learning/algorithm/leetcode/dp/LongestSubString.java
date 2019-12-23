package com.learning.algorithm.leetcode.dp;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * 找出两个字符串的最长的公共连续子串
 * 输入为两行字符串（可能包含空格），长度均小于等于50.
 * 输出为一个整数，表示最长公共连续子串的长度。
 *
 * @author xuechongyang
 */
public class LongestSubString {

    public static int findLCS(String s1, String s2) {
        if (StringUtils.isEmpty(s1) || StringUtils.isEmpty(s2)) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int dp[][] = new int[s1.length()][s2.length()];
        for (int i = 1; i < s1.length(); i++) {
            for (int j = 1; j < s2.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                    if (dp[i][j] > max) {
                        max = dp[i][j];
                    }
                } else {
                    dp[i][j] = 0;
                }
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        return max;
    }

    @Test
    public void test() {
        int result = LongestSubString.findLCS("sbcde", "deabcd");
        System.out.println("最长公共连续子串长度=" + result);
    }
}
