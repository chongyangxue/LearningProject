package com.learning.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.learning.Thread.NamedThreadFactory;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * GuavaCache Demo.
 *
 * @author xuechongyang
 */
public class GuavaCacheDemo {

    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(1, 3, 10, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(100), new NamedThreadFactory("test-thread"), new ThreadPoolExecutor.AbortPolicy());

    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .weakKeys()
//            .weakValues()
            .softValues() //LRU策略GC
            .expireAfterWrite(1, TimeUnit.DAYS)
            .expireAfterAccess(1, TimeUnit.DAYS)
            .refreshAfterWrite(1, TimeUnit.MINUTES)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    System.out.println("loading key:" + key);
                    return "load value";
                }

                @Override
                public ListenableFuture<String> reload(final String key, final String oldValue) throws Exception {
                    ListenableFutureTask<String> task = ListenableFutureTask.create(() -> "reload value");
                    EXECUTOR.execute(task);
                    System.out.println("reloading key:" + key);
                    return task;
                }
            });

    @Test
    public void testLoad() throws ExecutionException {
        String key = "test";
        for (int i = 0; i < 10; i++) {
            System.out.println(localCache.get(key));
        }
    }

    @Test
    public void testCallable() throws ExecutionException {
        String key = "test";
        String value = localCache.get(key, () -> "callable value");
        System.out.println(value);
    }

    @Test
    public void testReload() throws Exception {
        String key = "test";
        System.out.println(localCache.get(key));
        localCache.refresh(key);
        for (int i = 0; i < 10; i++) {
            System.out.println(localCache.get(key));
            TimeUnit.MILLISECONDS.sleep(400);
        }
    }
}
