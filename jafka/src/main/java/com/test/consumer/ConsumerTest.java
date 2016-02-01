package com.test.consumer;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.sohu.jafka.consumer.Consumer;
import com.sohu.jafka.consumer.ConsumerConfig;
import com.sohu.jafka.consumer.ConsumerConnector;
import com.sohu.jafka.consumer.MessageStream;
import com.sohu.jafka.producer.serializer.StringDecoder;
import com.sohu.jafka.utils.ImmutableMap;

public class ConsumerTest {

    public static void main(String[] args) throws InterruptedException {
       
        Properties props = new Properties();
        props.put("zk.connect", "192.168.6.143:2181,192.168.6.207:2181,192.168.6.208:2181");
        props.put("groupid", "test_group");
        //
        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        ConsumerConnector connector = Consumer.create(consumerConfig);

        Map<String, List<MessageStream<String>>> topicMessageStreams = connector.createMessageStreams(
                ImmutableMap.of("test", 2), new StringDecoder());
        List<MessageStream<String>> streams = topicMessageStreams.get("test");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        final AtomicInteger count = new AtomicInteger();
        for (final MessageStream<String> stream : streams) {
            executor.submit(new Runnable() {

                public void run() {
                    for (String message : stream) {
                        System.out.println(count.incrementAndGet() + " => " + message);
                    }
                }
            });
        }
        //
        executor.awaitTermination(1, TimeUnit.HOURS);
    }
}