package Concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Test;

/**
 * 实现 Queue 的类是：
 * java.util.LinkedList 已经进行了改进来实现 Queue。
 * java.util.PriorityQueue 非线程安全的优先级对列（堆）实现，根据自然顺序或比较器返回元素。
 * java.util.concurrent.ConcurrentLinkedQueue 快速、线程安全的、无阻塞 FIFO 队列。
 * @author Sachiel
 *
 */
public class Queue {

	@Test
	public void test() {
		ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
		queue.add("a");
		queue.add("b");
		queue.add("c");
		queue.add("d");
		String item = null;
		while((item = queue.poll()) != null){
			
			System.out.println(item);
		}
	}
}
