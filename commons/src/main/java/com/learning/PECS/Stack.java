package com.learning.PECS;

import java.util.Collection;
import java.util.LinkedList;

public class Stack<E> {
	private LinkedList<E> list = new LinkedList<E>();

	public void push(E e){
		list.push(e);
	}
	
	public E pop(){
		return list.pop();
	}
	
	public void pushAll(Iterable<? extends E> src){
		for(E e : src){
			list.push(e);
		}
	}
	
	public void popAll(Collection<? super E> dst){
		for(E e : list){
			dst.add(e);
		}
	}
	
}
