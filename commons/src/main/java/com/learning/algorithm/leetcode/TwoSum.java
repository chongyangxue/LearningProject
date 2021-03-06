package com.learning.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers, find two numbers such that they add up to a
 * specific target number.
 * 
 * The function twoSum should return indices of the two numbers such that they
 * add up to the target, where index1 must be less than index2. Please note that
 * your returned answers (both index1 and index2) are not zero-based.
 * 
 * You may assume that each input would have exactly one solution.
 * 
 * Input: numbers={2, 7, 11, 15}, target=9 Output: index1=1, index2=2
 * 
 * @author chongyangxue
 * 
 */
public class TwoSum {
	/**
	 * 如果数组是排好序的
	 * 
	 * @param numbers
	 * @param target
	 * @return
	 */
	public static int[] twoSumSorted(int[] numbers, int target) {
		int[] index = new int[2];
		int max = target - numbers[0];
		int[] temp = new int[max + 1];
		for(int i = 0; i < max + 1; i++){
			temp[i] = -1;
		}
		for(int i = 0; i < numbers.length; i++){
			if(temp[numbers[i]] != -1){
				index[0] = temp[numbers[i]] + 1;
				index[1] = i + 1;
				return index;
			}
			int result = target - numbers[i];
			if(result < 0)
				break;
			else{
				temp[result] = i;
			}
		}
		return index;
	}

	/**
	 * 如果数组正数，无序
	 * 
	 * @param numbers
	 * @param target
	 * @return
	 */
	public static int[] twoSum(int[] numbers, int target) {
		int[] index = new int[2];
		int[] temp = new int[100];
		for(int i = 0; i < numbers.length; i++){
			int result = target - numbers[i];
			temp[result] = i + 1;
		}
		for(int i = 1; i < temp.length; i++){
			if(temp[i] != 0){
				index[0] = temp[i];
				index[1] = temp[target - i];
			}
		}
		if(index[0] > index[1]){
			int tmp = index[0];
			index[0] = index[1];
			index[1] = tmp;
		}
		return index;
	}

	/**
	 * 如果数组整形无序
	 * 
	 * @param numbers
	 * @param target
	 * @return
	 */
	public static int[] twoSumAll(int[] numbers, int target) {
		int[] index = new int[2];
		Map<Integer, Integer> map = new HashMap<>();
		for(int i = 0; i < numbers.length; i++){
			int key = target - numbers[i];
			if(map.get(key) != null){
				index[0] = map.get(key);
				index[1] = i;
			}else{
				map.put(numbers[i], i);
			}
		}

		return index;
	}

	public static void main(String[] args) {
		int[] numbers = { 1, 4, 3, 90 };
		int target = 5;
		int[] result = twoSumAll(numbers, target);
		System.out.println(result[0] + "      " + result[1]);
	}
}
