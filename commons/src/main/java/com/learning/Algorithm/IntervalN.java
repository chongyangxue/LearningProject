package com.learning.Algorithm;

import org.junit.Test;

/**
 * 循环打印数组，每隔N个数打印一个数并移除
 * @author Sachiel
 *
 */
public class IntervalN {
	public void intervalN(int[] a, int n){
		int count = 0;
		int printCount = 0;
		int index = 0;
		while(printCount < n){
			if(index + 1 == a.length)
				index = 0;
			while(a[index] == -1){
				index++;
			}
			if(count == n){
				System.out.println(a[index]);
				printCount++;
				count = 0;
				a[index] = -1;
			}

			count++;
			index++;
		}
	}
	
	@Test
	public void testIntervalN(){
		int[] a = new int[]{1,2,3,4,5,6,7,8,9};
		intervalN(a, 3);
	}
}
