package com.learning.guava;

import com.google.common.collect.Maps;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * JDK引用队列DEMO
 * 弱引用，软引用可以和一个引用队列（ReferenceQueue）联合使用，如果弱引用所引用的对象被GC，JVM就会把这个引用加入到与之关联的引用队列中。
 *
 * 我们可以从queue中获取到相应的对象信息，当对象被GC, 进行额外的处理: 比如反向操作，数据清理等。
 *
 * ReferenceQueue的poll方法是非阻塞的；remove方法是阻塞的，一直等待查询到队列中元素为止。
 *
 * @author xuechongyang
 */
public class ReferenceQueueTest {

    private static ReferenceQueue<byte[]> rq = new ReferenceQueue<>();

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            int count = 0;
            WeakReference k;
            try {
                while ((k = (WeakReference) rq.remove()) != null) {
                    System.out.println((count++) + "回收了:" + k);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();

        Map<Object, Object> map = Maps.newHashMap();
        Object value = new Object();
        for (int i = 0; i < 10000; i++) {
            //试着改变size的大小，观察GC的变化
            int size = 1024*1024;
            byte[] bytes = new byte[size];
            WeakReference<byte[]> weakReference = new WeakReference<>(bytes, rq);
            map.put(weakReference, value);
        }
        System.out.println("map.size -> " + map.size());
    }

}
