package concurrent.old;


public class RunnableClass implements Runnable {
	private String name;
	private static int files = 100;

	public RunnableClass(String name) {
		this.name = name;
	}

	public synchronized void readFile() {
		synchronized(RunnableClass.class) {
			files--;
			System.out.println(name + " run: files remain " + files);
		}
	}

	public void run() {
		try {
			while(files > 0) {
				for(int i = 0; i < files; i++) {
					int currentThreadNum = ThreadPool.atomicInteger.getAndDecrement();
					//保证同时最多两个线程
					if(currentThreadNum > 2) {
						System.out.println(name + " Thread stopped...");
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
