package com.learning.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.learning.Thread.NamedThreadFactory;

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
                    return "loadKey";
                }

                @Override
                public ListenableFuture<String> reload(final String key, final String oldValue) {
                    // asynchronous!
                    ListenableFutureTask<String> task = ListenableFutureTask.create(() -> key + "reloadMethod");
                    EXECUTOR.execute(task);
                    return task;
                }

            });

    public static void main(String[] args) throws ExecutionException {
        String value = localCache.get("key");
        System.out.println(value);

        value = localCache.get("key1", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "callable value";
            }
        });
        System.out.println(value);
    }
}
