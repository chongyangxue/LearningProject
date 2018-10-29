package com.learning.guava;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuechongyang on 17/5/5.
 */
public class ListenableFutureDemo {

    class callable implements Callable<List<Integer>> {

        @Override
        public List<Integer> call() throws Exception {
            System.out.println("call execute..");
            TimeUnit.SECONDS.sleep(1);
            return Lists.newArrayList(1, 2, 3);
        }
    }

    @Test
    public void testFutureAllOf() {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        List<ListenableFuture<List<Integer>>> futureList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            ListenableFuture<List<Integer>> listenableFuture = executorService.submit(new callable());
            futureList.add(listenableFuture);
        }
//        ListenableFuture<List<Integer>> future = Futures.allAsList(futureList);
    }
}
