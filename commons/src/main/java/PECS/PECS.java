package PECS;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PECS {
	
	@Test
	public void testExtends(){
		Stack<Number> stack = new Stack<Number>();
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		stack.pushAll(list);
		
		List<Object> newList = new ArrayList<Object>();
		stack.popAll(newList);
	}
}
