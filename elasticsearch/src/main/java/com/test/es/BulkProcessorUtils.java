package com.test.es;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BulkProcessorUtils {
    private static Logger LOG = LoggerFactory.getLogger(BulkProcessorUtils.class);

    /**
     * flush刷新时间间隔
     */
    static TimeValue flushInterval = TimeValue.timeValueSeconds(5);

    /**
     * 并发bulk入库线程数
     */
    static int concurrentRequests = 1;

    /**
     * 每个bulk的条数阈值 达到就flush
     */
    static int bulkActions = 1000;

    private static final byte[] lock = new byte[0];

    private static volatile BulkProcessor processor;

    /**
     * 加一些公共字段
     * 
     * @param indexRequest
     * @return
     */
    public static IndexRequest encapsulateFields(IndexRequest indexRequest) throws Exception {
        try {
            Map<String, Object> map = indexRequest.sourceAsMap();
            DateTime dt = new DateTime();
            map.put("@date_time", dt);
            map.put("@timestamp", System.currentTimeMillis());
            indexRequest = indexRequest.source(map);
        } catch(Exception e) {
            LOG.error("error-----" + e);
            throw e;
        }

        LOG.info("index---------" + indexRequest.index());
        return indexRequest;
    }

    /**
     * Initialized global singleton
     * 
     * @param client
     *            elasticsearch client
     */
    public static void init(Client client) {
        if (processor == null) {
            synchronized (lock) {
                if (processor == null) {
                    processor = BulkProcessor.builder(client, new BulkListener())
                            .setConcurrentRequests(concurrentRequests).setBulkActions(bulkActions)
                            .setFlushInterval(flushInterval).build();
                }
            }
        }
    }

    public static void add(IndexRequest indexRequest) throws Exception {
        // LOG.info("add------start");
        if (indexRequest != null) {
            try {
                LOG.trace("index-------- " + indexRequest.index());
                processor.add(encapsulateFields(indexRequest)
                // .replicationType(ReplicationType.ASYNC)
                // .consistencyLevel(WriteConsistencyLevel.ONE)
                        );
                // processor.add(indexRequest);
            } catch(Exception e) {
                LOG.error("bulkerror----" + e.getMessage());
                throw e;
            }
        }
    }

    public static void flush() {
        processor.flush();
        LOG.info("flush----- over");
    }

    public static void close() {
        processor.close();
    }

    public static int atomicCounter() {
        return BulkListener.atomMap.size();
    }

}
