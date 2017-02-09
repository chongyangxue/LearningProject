package com.test.es;

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkProcessor.Listener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;

import java.util.Map;

public class BulkListener implements Listener {
    private static Logger LOG = Logger.getLogger(BulkListener.class);

    public static final Map<Long, Long> atomMap = Maps.newConcurrentMap();

    @Override
    public void beforeBulk(long executionId, BulkRequest request) {
        atomMap.put(executionId, System.currentTimeMillis());
    }

    @Override
    public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
        atomMap.remove(executionId);
    }

    @Override
    public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
        if (response.hasFailures()) {
            LOG.warn("buildFailureMessage " + response.buildFailureMessage());
        }
        atomMap.remove(executionId);
    }
}
