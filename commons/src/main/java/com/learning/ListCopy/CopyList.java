package com.learning.ListCopy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;
/**
 * 结论：各个形式的列表或者数组，底层都是用数组存储一组对象的引用，复制列表或者数组，并没有实际复制列表中的对象，只是复制了一组引用
 * @author chongyangxue
 *
 */
public class CopyList {
	@Test
	public void testCopyList(){
		System.out.println("-------------------------test-----------------------");
		String[] array = {"xue", "chong", "yang", "1987", "09", "09"};
		List<String> list = Arrays.asList(array);
		List<String> newList = new ArrayList<String>(list);
		newList.set(1, "changed");
		System.out.println("newList: " + newList);
		System.out.println("list: " +list.toString());
		
	}
	
	@Test
	public void testCopyList1(){
		System.out.println("-------------------------test1-----------------------");
		User[] users = {new User(0, "chongyang", "123", "haidian"),
				new User(1, "baozhe", "123", "chaoyang"),
				new User(2, "linye", "123", "fengtai")
		};
		List<User> list = Arrays.asList(users);
		List<User> newList = new ArrayList<User>(list);
		newList.remove(2);
		System.out.println("list's size: " + list.size());
		System.out.println("new list size: " + newList.size());
	}
	
	@Test
	public void testCopyList2(){
		System.out.println("-------------------------test2-----------------------");
		User[] users = {new User(0, "chongyang", "123", "haidian"),
				new User(1, "baozhe", "123", "chaoyang"),
				new User(2, "linye", "123", "fengtai")
		};
		List<User> list = Arrays.asList(users);
		List<User> newList = new ArrayList<User>(list);
		newList.get(0).setUsername("new name");

		for(User user : newList){
			System.out.println("newList--" + user.getUserid() + user.getUsername());
		}
		for(User user : list){
			System.out.println("list--" + user.getUserid() + "," + user.getUsername());
		}
	}
	
	@Test
	public void testCopyList3(){
		System.out.println("-------------------------test3-----------------------");
		User[] users = {new User(0, "chongyang", "123", "haidian"),
				new User(1, "baozhe", "123", "chaoyang"),
				new User(2, "linye", "123", "fengtai")
		};
		List<User> list = Arrays.asList(users);
		List<User> newList = new ArrayList<User>();
		newList.addAll(list);
		newList.get(0).setUsername("new name");

		for(User user : newList){
			System.out.println("newList--" + user.getUserid() + "," + user.getUsername());
		}
		for(User user : list){
			System.out.println("list--" + user.getUserid() + "," + user.getUsername());
		}
	}
	
	@Test
	public void testCopyList4(){
		System.out.println("-------------------------test4-----------------------");
		User[] users = {new User(0, "chongyang", "123", "haidian"),
				new User(1, "baozhe", "123", "chaoyang"),
				new User(2, "linye", "123", "fengtai")
		};
		List<User> list = Arrays.asList(users);
		List<User> newList = new CopyOnWriteArrayList<User>();
		newList.addAll(list);
		newList.get(0).setUsername("new name");

		for(User user : newList){
			System.out.println("newList--" + user.getUserid() + "," + user.getUsername());
		}
		for(User user : list){
			System.out.println("list--" + user.getUserid() + "," + user.getUsername());
		}
	}
	
	@Test
	public void testCopyArray(){
		System.out.println("-------------------------testCopyArray-----------------------");
		User[] array = {new User(0, "chongyang", "123", "haidian"),
				new User(1, "baozhe", "123", "chaoyang"),
				new User(2, "linye", "123", "fengtai")
		};
		User[] newArray = array.clone();
		newArray[0].setUsername("new name");

		for(User user : newArray){
			System.out.println("newArray--" + user.getUserid() + "," + user.getUsername());
		}
		for(User user : array){
			System.out.println("array--" + user.getUserid() + "," + user.getUsername());
		}
	}
	
	@Test
	public void testCopyArray1(){
		System.out.println("-------------------------testCopyArray1-----------------------");
		User[] array = {new User(0, "chongyang", "123", "haidian"),
				new User(1, "baozhe", "123", "chaoyang"),
				new User(2, "linye", "123", "fengtai")
		};
		User[] newArray = new User[array.length];
		System.arraycopy(array, 0, newArray, 0, array.length);
		newArray[0].setUsername("new name");

		for(User user : newArray){
			System.out.println("newArray--" + user.getUserid() + "," + user.getUsername());
		}
		for(User user : array){
			System.out.println("array--" + user.getUserid() + "," + user.getUsername());
		}
	}
	
}
