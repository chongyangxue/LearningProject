package com.threadTest_Runnable;


public class threadTest1 implements Runnable {
	private String name;
	private static int files = 100;

	public threadTest1(String name) {
		this.name = name;
	}

	public synchronized void readFile() {
		synchronized(threadTest1.class) {
			files--;
			System.out.println(name + " run: files remain " + files);
		}
	}

	public void run() {
		try {

			while(files > 0) {
				for(int i = 0; i < files; i++) {
					if(ThreadPool.getCurrentNum() > 2) {
						System.out.println(name + " Thread stopped...");
						ThreadPool.reduce();
						break;
					}
					readFile();
					Thread.sleep(100);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
