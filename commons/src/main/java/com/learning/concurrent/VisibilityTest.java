package com.learning.concurrent;

public class VisibilityTest {
	public static void main(String[] args) throws InterruptedException {
		ReferenceVisibilityThread refThread = new ReferenceVisibilityThread();
		refThread.start();

		Thread.sleep(1000);
		System.out.println("will set object...");
		refThread.setObj();
		Thread.sleep(1000);
		System.out.println("finish main");
		System.out.println("main thread getObj=" + refThread.getObj());
	}
}

class ReferenceVisibilityThread extends Thread {
	 private Object obj;

	public void run() {
		int i = 0;
		System.out.println("start loop.");
		while (getObj() == null) {
			i++;
		}
		System.out.println("finish loop,i=" + i);
	}

	public void setObj() {
		obj = new Object();
	}

	public Object getObj() {
		return obj;
	}
}
