package concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentMap {
	public static void main(String[] args) throws Exception {
		ConcurrentMap demo = new ConcurrentMap();
		for(int i = 0; i < 10; i++){
			demo.testMap();
			Thread.sleep(500L);
		}
	}

	/**
	 * 结果不一定会输出0是因为HashMap在扩容时导致了重新进行hash计算，而rehash过程可能导致死循环
	 * 改为final Map<Long, String> map = new HashMap<Long, String>(1000);
	 * 或者使用ConcurrentHashMap
	 * @throws Exception
	 */
	public void testMap() throws Exception {
		final Map<Long, String> map = new ConcurrentHashMap<Long, String>();
		final int count = 200;
		final AtomicInteger checkNum = new AtomicInteger(0);
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(100);
		map.put(0L, "www.sachiel.net");
		for(int j = 0; j < count; j++){
			newFixedThreadPool.submit(new Runnable() {
				public void run() {
					map.put(System.nanoTime() + new Random().nextLong(),
							"www.sachiel.net");
					String obj = map.get(0L);
					if(obj == null){
						checkNum.incrementAndGet();
					}
				}
			});
		}
		newFixedThreadPool.awaitTermination(1, TimeUnit.SECONDS);
		newFixedThreadPool.shutdown();

		System.out.println(checkNum.get());
	}
}
