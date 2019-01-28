package com.learning.off_heap.bytebuffer;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.ExpiryPolicy;
import org.ehcache.spi.loaderwriter.CacheLoaderWriter;
import org.junit.Test;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * @author xuechongyang
 *
 */
public class EhcacheTest {

    private static Cache<String, Property> cache;

    static {
        ResourcePools resourcePools = ResourcePoolsBuilder.newResourcePoolsBuilder()
                .heap(1000, EntryUnit.ENTRIES)
                .offheap(480, MemoryUnit.MB)
                .build();

        CacheConfiguration<String, Property> configuration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, Property.class, resourcePools)
                //设置过期时间
                .withExpiry(new ExpiryPolicy<String, Property>() {
                    @Override
                    public Duration getExpiryForCreation(String key, Property value) {
                        return Duration.ofHours(1);
                    }

                    @Override
                    public Duration getExpiryForAccess(String key, Supplier<? extends Property> value) {
                        return null;
                    }

                    @Override
                    public Duration getExpiryForUpdate(String key, Supplier<? extends Property> oldValue, Property newValue) {
                        return Duration.ofHours(1);
                    }
                })
                //设置read-through加载器
                .withLoaderWriter(new CacheLoaderWriter<String, Property>() {
                    @Override
                    public Property load(String key) throws Exception {
                        return null;
                    }

                    @Override
                    public void write(String key, Property value) throws Exception {

                    }

                    @Override
                    public void delete(String key) throws Exception {

                    }
                })
                .build();

        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("testCache", configuration)
                .build(true);

        cache = cacheManager.getCache("testCache", String.class, Property.class);

    }

    @Test
    public void invoke() {

        int size = 170000;

        for (int i = 0; i < size; ++i) {
            String key = String.format("key_%s", i);
            Property property = new Property(key, i);
            cache.put(key, property);
        }
        System.out.println("write down");

        for (int i = 0; i < size; ++i) {
            String key = String.format("key_%s", i);
            Property property = cache.get(key);
            if (property == null) {
                System.err.println("cache invalid, key: " + key);
                throw new RuntimeException("cache invalid");
            }

            if (i % 10000 == 0) {
                System.out.println("read " + i);
            }
        }
    }
}
