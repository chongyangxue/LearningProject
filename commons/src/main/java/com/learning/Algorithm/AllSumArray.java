package com.learning.Algorithm;

/**
 * 题目描述：输入两个整数n和m，从数列1，2，3.......n 中随意取几个数，这里同一个数不能重复取，使其和等于m， 
 * 要求将其中所有的可能组合列出来。
 * 
 * @author Sachiel
 * 
 */
public class AllSumArray {
	public static void printArray(int n, int sum) {
		if(sum < 1 || n < 1)
			return;
		if(sum < n)
			n = sum;
		if(sum == n || sum == 1){
			System.out.print(n + " ");
			return;
		}

		printArray(n - 1, sum);
		printArray(n - 1, sum - n);
	}

	public static void main(String[] args) {
		printArray(10, 12);
	}
}
