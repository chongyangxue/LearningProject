package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ReorderExample {
	 int a = 0;
	 boolean flag = false;
	 

	public void writer() {
			a = 1; // 1
			flag = true; // 2
	}

	public void reader() {	
			if (flag) { // 3
				int i = a ; // 4
				if(i == 0){
					System.out.println(i);
				}
				flag = false;
				a = 0;
			}
	}

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		 final CyclicBarrier cb = new CyclicBarrier(2);
		final ReorderExample example = new ReorderExample();
			threadPool.execute(new Runnable() {
				@Override
				public void run() {

					while(true){
							try {
								cb.await();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BrokenBarrierException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	
						example.writer();
					}

				}
			});
			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					while(true) {

							try {
								cb.await();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BrokenBarrierException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	
						example.reader();
					}

				}
			});
			
			threadPool.shutdown();
		}
}
