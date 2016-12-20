package com.learning.concurrent;

public class VisibilityTest3 {
	public static void main(String[] args) {
		new ThisEscape();
		System.out.println("finish main");
	}
}

class ThisEscape {
	private Object o;

	public ThisEscape() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				doSomething();
			}
		});
		t.start();

		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		o = new Object();
	}

	public void doSomething() {
		System.out.println("start loop...");
		int nullTimes = 0;
		while (o == null) {
			nullTimes++;
		}
		System.out.println("finish loop, null times=" + nullTimes);
	}
}
