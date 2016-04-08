/** 
 * File: CuratorDemo.java
 * Copyright (C), 2015-2016 ÷–”Ø”≈¥¥  Tech.Co.Ltd.All Rights Reserved.
 */
package com.test.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * Description: 
 * Author: Sachiel 
 * Date: 2016-3-10 
 */
public class CuratorDemo {
    
    private DataWatcher dataWatcher = new DataWatcher();
    
    private ChildrenWatcher childrenWatcher = new ChildrenWatcher();
    
    private CuratorFramework client;

    public CuratorFramework getCurator() {
        return client;
    }
    
    public int init(String zookeeperConnectionString) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        try {
            client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
            client.start();
        } catch (Exception e) {
            System.out.println("Zookeeper client init failed");
            return -1;
        }

        return 1;
    }
    
    private class CacheListener implements PathChildrenCacheListener {

        @Override
        public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
            String path = event.getData().getPath();
            switch (event.getType()) {
                case CHILD_ADDED: {
                    System.out.println("new node:" + path);
//                    client.getData().usingWatcher(dataWatcher).forPath(path);
                    break;
                }
                case CHILD_REMOVED: {
                    System.out.println("delete node:" + path);
                    break;
                }
            }
        }
        
    }
    
    private class DataWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            System.out.println("data changed:" + event.getPath());
            try {
                client.getData().usingWatcher(dataWatcher).forPath(event.getPath());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
    private class ChildrenWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            System.out.println(event.getType() + ": " + event.getPath());
            try {
                client.getChildren().usingWatcher(childrenWatcher).forPath(event.getPath());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public void testCache() {
        String path = "/gw/defender/BJ_T_TEST/template";
        PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.getListenable().addListener(new CacheListener());
        try {
            cache.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void testWatch() throws Exception {
        String path = "/gw/defender/BJ_T_TEST/template";
        client.getChildren().usingWatcher(childrenWatcher).forPath(path);
    }

    public static void main(String[] args) throws Exception {
        CuratorDemo demo = new CuratorDemo();
        demo.init("192.168.6.143:2181,192.168.6.207:2181,192.168.6.208:2181");
        demo.testCache();
        while (true) {
            Thread.sleep(2000);
        }
    }
}
