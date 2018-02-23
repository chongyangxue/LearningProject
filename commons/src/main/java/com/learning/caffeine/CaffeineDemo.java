package com.learning.caffeine;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine Cache Demo.
 *
 * @author xuechongyang
 */
public class CaffeineDemo {

    private static AsyncLoadingCache<String, String> localCache = Caffeine.newBuilder()
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .initialCapacity(4000)
            .buildAsync(key -> {
                System.out.println("loading key:" + key);
                return "load value";
            });

    @Test
    public void testLoad() throws Exception {
        for (int i = 0; i < 10; i++) {
            CompletableFuture future = localCache.get(i + "");
            System.out.println(future.get());
        }
    }

}
