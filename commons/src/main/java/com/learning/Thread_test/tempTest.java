package com.learning.Thread_test;

public class tempTest extends Thread {
	private String name;
	private static int files = 100;

	public tempTest(String name) {
		this.name = name;
	}

	public void readFile() {
		synchronized(tempTest.class) {
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
					sleep(100);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
