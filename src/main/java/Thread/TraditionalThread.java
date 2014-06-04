package Thread;

public class TraditionalThread {
	public static void main(String[] args) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(500);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName());
				}
			}
		};
		thread.start();

		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(500);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName());
				}
			}

		});
		thread2.start();

		new Thread(
				new Runnable() {
					@Override
					public void run() {
						while(true) {
							try {
								Thread.sleep(500);
							}catch(InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println("Runnable :" + Thread.currentThread().getName());
						}
					}
 				}
				) {
			public void run() {
				while(true) {
					try {
						Thread.sleep(500);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("thread:" + Thread.currentThread().getName());
				}
			}
		}.start();
	}
}
