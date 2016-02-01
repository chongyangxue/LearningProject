package com.test.producer;

import java.util.Properties;

import com.sohu.jafka.producer.Producer;
import com.sohu.jafka.producer.ProducerConfig;
import com.sohu.jafka.producer.StringProducerData;
import com.sohu.jafka.producer.serializer.StringEncoder;

public class ProducerExample {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("zk.connect", "192.168.6.143:2181,192.168.6.207:2181,192.168.6.208:2181");
        props.put("serializer.class", StringEncoder.class.getName());
        //
        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);
        //
        StringProducerData data = new StringProducerData("test");
        for (int i = 0; i < 100; i++) {
            data.add("Hello world #" + i);
        }
        
        try {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                producer.send(data);
            }
            long cost = System.currentTimeMillis() - start;
            System.out.println("send 10000 message cost: " + cost + " ms");
        } finally {
            producer.close();
        }
    }
}