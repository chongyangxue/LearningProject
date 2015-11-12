package concurrent.old;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;

public class ConcurrentList {
	@Test
	public void testArrayList(){
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < 20; i++){
			list.add(i);
		}
		
		/**
		 * This will cast java.util.ConcurrentModificationException
		 */
		for(Integer s : list){
			if(s%2 == 0){
				list.add(s + 100);
				list.remove(s);
			}
		}
		
		for(Integer s : list){
			System.out.print(s + " ");
		}
	}
	
	public void testCopyOnWriteArrayList (){
		List<Integer> list = new CopyOnWriteArrayList<Integer>();
		for(int i = 0; i < 20; i++){
			list.add(i);
		}
		
		/**
		 * This will success
		 */
		for(Integer s : list){
			if(s%2 == 0){
				list.remove(s);
			}
		}
		
		for(Integer s : list){
			System.out.print(s + " ");
		}
	}
	
	
}
