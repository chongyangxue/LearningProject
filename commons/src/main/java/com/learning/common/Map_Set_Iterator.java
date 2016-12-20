package com.learning.common;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Map_Set_Iterator {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "001");
		map.put("name", "xue");
		map.put("degree", "master");
		Set<String> set = map.keySet();
		Iterator i = set.iterator();

		while(i.hasNext()) {
			Object key = i.next();
			System.out.println(key + ": " + map.get(key));
		}
	}
}
