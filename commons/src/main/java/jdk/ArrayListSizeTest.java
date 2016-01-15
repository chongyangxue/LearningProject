package jdk;

import java.util.ArrayList;
import java.util.List;

/**
 * Integer类型最大长度为：
 * String类型最大长度为： 25764983
 * @author Sachiel
 *
 */
public class ArrayListSizeTest {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		int i = 0;
		try {
			System.out.println("i =" + i);
			for(;; i++) {
				list.add("This is ArrayList test! This is ArrayList test! This is ArrayList test!");
			}
		}catch(java.lang.OutOfMemoryError e) {
			System.out.println("i =" + i);
		}

	}
}
