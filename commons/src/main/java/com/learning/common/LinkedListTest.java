package com.learning.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Test;

public class LinkedListTest {
	@Test
	public void testLinkedList() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("Hello");
		arrayList.add("world");
		arrayList.add("I");
		arrayList.add("Love");
		arrayList.add("You!");
		LinkedList<String> linkList = new LinkedList<String>(arrayList);
		for(Iterator<String> iterator = linkList.iterator(); iterator.hasNext();) {
			if(iterator.next().equals("I")) {
				iterator.remove();
			}
		}
		System.out.println(linkList);
		if(linkList.contains("Love")) {
			linkList.add(linkList.indexOf("Love"), "Who");
		}
		System.out.println(linkList);
		
		Collection<String> collection = linkList;
		Collections.sort(linkList);
		System.out.println(linkList);
	}
}
