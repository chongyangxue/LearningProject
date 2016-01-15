package concurrent.old;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {
	private static int ThreadNum = 0;

	public static synchronized int getCurrentNum() {
		return ThreadNum;
	}
	
	/*  用AtomicInteger来替换
	public static synchronized void reduce() {
		ThreadNum--;
	}*/
	
	public static AtomicInteger atomicInteger = new AtomicInteger(5);

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new RunnableClass("A"));
		executor.execute(new RunnableClass("B"));
		executor.execute(new RunnableClass("C"));
		executor.execute(new RunnableClass("D"));
		executor.execute(new RunnableClass("E"));
		executor.shutdown();
	}
}
