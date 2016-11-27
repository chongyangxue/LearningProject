package com.learning.iginte;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

/**
 * Created by xuechongyang on 16/11/25.
 */
public class IgnitePutDemo {
    public static void main(String[] args) {
        try {
            Ignite ignite = Ignition.start("example-ignite.xml");
            IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCacheName");

            // Store keys in cache (values will end up on different cache nodes).
            for (int i = 0; i < 10; i++) {
                cache.put(i, Integer.toString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
