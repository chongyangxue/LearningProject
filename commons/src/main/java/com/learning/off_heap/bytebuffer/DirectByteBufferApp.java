package com.learning.off_heap.bytebuffer;

import com.learning.Serialize.Hessian2Serializer;
import org.junit.Test;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class DirectByteBufferApp  {

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
                System.out.println("cache invalid, key: " + key);
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
            Property value = createProperty(key, i);
            map.put(key, value);
        }

        return map;
    }

    private Property createProperty(String key, int i) {
        return new Property(key, i);
    }

    static void free(ByteBuffer byteBuffer) {
        if (byteBuffer.isDirect()) {
            ((DirectBuffer) byteBuffer).cleaner().clean();
        }
    }
}
