package com.learning.NIO.httpAsyncClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by xuechongyang on 16/12/30.
 *
 * 源码分析blog: http://www.cnblogs.com/zemliu/p/3719292.html
 */
public class HttpAsyncClientDemo {

    @Test
    public void test() throws InterruptedException, ExecutionException, IOException {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        httpclient.start();
        final HttpGet request = new HttpGet("http://www.sogou.com");
        request.setHeader("Connection", "close");
        List<Future<HttpResponse>> respList = new LinkedList<>();
        for (int i = 0; i < 50; i++) {
            respList.add(httpclient.execute(request, null));
        }
        for (Future<HttpResponse> response : respList) {
            response.get().getStatusLine();
             System.out.println(response.get().getStatusLine());
        }
        httpclient.close();
    }
}
