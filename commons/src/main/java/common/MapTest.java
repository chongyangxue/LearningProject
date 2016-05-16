package common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

public class MapTest {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> syncMap = Collections.synchronizedMap(map); //获取内同步的map
		Map<String, ?> mapTest;
		syncMap.put("name", "zhang");
		syncMap.put("address", "bjtu");
		syncMap.put("major", "cs");

		for(Map.Entry<String, String> entry : syncMap.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		System.out.println("-------------------------------------");
		mapTest = map;
		Set<String> keySet = mapTest.keySet();
		for(String key : keySet) {
			System.out.println(key.toString());
		}
		
		Map<String, String> map1 = new ConcurrentHashMap<String, String>();

	}
	
}
