package Thread;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InterruptThread {

    @Test
    public void testInterrupt() {
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(new Runnable() {
            @Override public void run() {
                while (true) {
                    if (Thread.interrupted()) {
                        return;
                    }
                    System.out.println(Thread.currentThread().getName());
                    Thread.currentThread().interrupt();
                }
            }
        });

    }
}
