package Thread;

public class TraditionalSynchonized {
	public static void main(String[] args) {
		new TraditionalSynchonized().init();
	}

	private void init() {
		final Outputer outputer = new Outputer();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(10);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
					outputer.output2("xuechongyang");
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(10);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
					outputer.output3("Hello World!");
				}
			}
		}).start();
	}

	static class Outputer {
		public void output1(String name) {
			int len = name.length();
			synchronized (this) {
				for(int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}

		public synchronized void output2(String name) {
			int len = name.length();
			for(int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
		
		//output1()中需要用synchronized(Outputer.class)
		public static synchronized void output3(String name) {
			int len = name.length();
			for(int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
	}

}
