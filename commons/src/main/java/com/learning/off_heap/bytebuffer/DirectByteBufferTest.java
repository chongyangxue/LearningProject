package com.learning.off_heap.bytebuffer;

import com.learning.Serialize.Hessian2Serializer;
import org.junit.Test;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 由JDK提供的堆外内存访问API只能申请到一个类似一维数组的ByteBuffer, JDK并未提供基于堆外内存的实用数据结构实现(如堆外的Map、Set),
 * 因此想要实现Cache的功能只能在write()时先将数据put()到一个堆内的HashMap, 然后再将整个Map序列化后MoveIn到DirectMemory, 取缓存则反之.
 * 由于需要在堆内申请HashMap, 因此可能会导致多次Full GC. 这种方式虽然可以使用堆外内存, 但性能不高、无法发挥堆外内存的优势.
 *
 * 幸运的是开源界的前辈开发了诸如Ehcache、MapDB、Chronicle Map等一系列优秀的堆外内存框架,
 * 使我们可以在使用简洁API访问堆外内存的同时又不损耗额外的性能.
 */
public class DirectByteBufferTest {

    static final int SIZE = 170000;

    @Test
    public void invoke() {
        Map<String, Property> map = createInHeapMap(SIZE);

        // move in off-heap
        byte[] bytes = Hessian2Serializer.serialize(map);
        ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        // for gc
        map = null;
        bytes = null;
        System.out.println("write down");
        // move out from off-heap
        byte[] offHeapBytes = new byte[buffer.limit()];
        buffer.get(offHeapBytes);
        Map<String, Property> deserMap = Hessian2Serializer.deserialize(offHeapBytes);
        for (int i = 0; i < SIZE; ++i) {
            String key = "key-" + i;
            Property property = deserMap.get(key);
            if (property == null) {
                System.err.println("cache invalid, key: " + key);
                throw new RuntimeException("cache invalid");
            }
            if (i % 10000 == 0) {
                System.out.println("read " + i);
            }
        }
        free(buffer);
    }

    private Map<String, Property> createInHeapMap(int size) {
        Map<String, Property> map = new ConcurrentHashMap<>(size);
        for (int i = 0; i < size; ++i) {
            String key = "key-" + i;
            Property value = new Property(key, i);
            map.put(key, value);
        }

        return map;
    }

    static void free(ByteBuffer byteBuffer) {
        if (byteBuffer.isDirect()) {
            ((DirectBuffer) byteBuffer).cleaner().clean();
        }
    }
}
