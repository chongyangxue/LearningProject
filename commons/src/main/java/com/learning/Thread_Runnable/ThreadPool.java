package com.learning.Thread_Runnable;

public class ThreadPool {
	private static int CurrentNum = 0;

	public static synchronized int getCurrentNum() {
		return CurrentNum;
	}

	public static synchronized void reduce() {
		CurrentNum--;
	}

	public void startThread(String name) {
		new Thread(new threadTest1(name)).start();
		CurrentNum++;
	}

	public static void main(String[] args) {
		ThreadPool pool = new ThreadPool();
		pool.startThread("A");
		pool.startThread("B");
		pool.startThread("C");
		pool.startThread("D");
		pool.startThread("E");
	}
}
