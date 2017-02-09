package com.test.es;

import org.elasticsearch.action.index.IndexRequest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xuechongyang on 17/2/9.
 */
public class EsTest {

    private Logger logger = LoggerFactory.getLogger(EsTest.class);

    @Before
    public void before() {
        String esname = System.getProperty("esname");
        String eshost = System.getProperty("eshost");
        EsClientUtils.initClient(esname, eshost);
        BulkProcessorUtils.init(EsClientUtils.getClient());
    }

    @Test
    public void addGwStatIndex(String content) {
        try {
            String date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
            StringBuilder indexName = new StringBuilder();
            indexName.append("es_test").append("-").append(date);
            IndexRequest indexRequest = new IndexRequest(indexName.toString(), "detail");
            indexRequest.source(content);
            BulkProcessorUtils.add(indexRequest);
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
