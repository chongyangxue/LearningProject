package com.learning.Algorithm.Leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xuechongyang
 *         给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 */
public class FourSum {

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int m = 0; m < nums.length; m++) {
            if (m > 0 && nums[m] == nums[m - 1]) {
                continue;
            }
            int loopTarget = target - nums[m];
            for (int i = m + 1; i < nums.length; i++) {
                if (i > m + 1 && nums[i] == nums[i - 1]) {
                    continue;
                }
                for (int j = i + 1, k = nums.length - 1; j < k; ) {
                    System.out.printf("m=%d,i=%d,j=%d,k=%d", m, i, j, k);
                    System.out.println();
                    if (nums[i] + nums[j] + nums[k] == loopTarget) {
                        result.add(Arrays.asList(nums[m], nums[i], nums[j], nums[k]));
                        while (j < k && nums[k] == nums[k - 1]) {
                            k--;
                        }
                        while (j < k && nums[j] == nums[j + 1]) {
                            j++;
                        }
                        j++;
                        k--;
                    } else if (nums[i] + nums[j] + nums[k] > loopTarget) {
                        while (j < k && nums[k] == nums[k - 1]) {
                            k--;
                        }
                        k--;
                    } else if (nums[i] + nums[j] + nums[k] < loopTarget) {
                        while (j < k && nums[j] == nums[j + 1]) {
                            j++;
                        }
                        j++;
                    }
                }
            }
        }
        return result;
    }

    @Test
    public void test() {
//        int[] numbers = {-4,-1,-1,0,1,2};
//        int[] numbers = {-3,-2,-1,0,0,1,2,3};
//        int[] numbers = {0,0,0,0};
        int[] numbers = {-5,-4,-3,-2,1,3,3,5};
        List<List<Integer>> result = fourSum(numbers, -11);
        System.out.println(result);
    }
}
