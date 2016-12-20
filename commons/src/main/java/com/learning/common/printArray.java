package com.learning.common;
import java.lang.reflect.Array;

public class printArray {

	public static void printObject(Object obj) {
		System.out.println(obj.getClass().getName());

		if(obj.getClass().isArray()) {
			System.out.println("This is array");

			for(int i = 0; i < Array.getLength(obj); i++) {
				System.out.println(Array.get(obj, i));
			}
		}
		else {
			System.out.println(obj);
		}
	}

	public static void main(String[] args) {
		int a1 = 1;
		int[] a2 = new int[] {1, 2, 3};
		printObject(a1);
		printObject(a2);

	}

}
