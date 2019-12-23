package com.learning.guava;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by xuechongyang on 17/5/5.
 */
public class SettableFutureDemo {


    private Integer getValue() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Test
    public void testFutureAllOf() {
        SettableFuture<Integer> settableFuture = SettableFuture.create();
        ListenableFuture<Integer> future = Futures.immediateFuture(getValue());
        try {
            Integer value = settableFuture.get(100, TimeUnit.MILLISECONDS);
            System.out.println(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
