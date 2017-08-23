package com.learning.Thread;

public class ThreadCommunicationTest {

    public static void main(String[] args) {
        final Business business = new Business();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                business.child();
            }
        }).start();

	/*	for(int i = 0; i < 50; i++) {
            business.main();
		}*/

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                business.main();
            }
        }).start();
    }

    static class Business {
        private boolean mutex = true;

        public synchronized void main() {
            while (mutex) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 20; i++) {
                System.out.println("The main thread running..." + i);
            }
            mutex = true;
            this.notify();
        }

        public synchronized void child() {
            while (!mutex) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 10; i++) {
                System.out.println("Child thread running! ---" + i);
            }
            mutex = false;
            this.notify();
        }
    }
}