package com.learning.iginte;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

/**
 * Created by xuechongyang on 16/11/25.
 */
public class IgniteGetDemo {
    public static void main(String[] args) {
        try {
            Ignite ignite = Ignition.start("example-ignite.xml");
            IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCacheName");

            for (int i = 0; i < 10; i++) {
                System.out.println("Got [key=" + i + ", val=" + cache.get(i) + ']');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
