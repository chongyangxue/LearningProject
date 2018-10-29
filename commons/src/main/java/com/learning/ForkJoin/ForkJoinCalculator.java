package com.learning.ForkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @author xuechongyang
 *
 * 使用了 ForkJoinPool 的实现逻辑全部集中在了 compute() 这个函数里，仅用了14行就实现了完整的计算过程。
 * 特别是，在这段代码里没有显式地“把任务分配给线程”，只是分解了任务，而把具体的任务到线程的映射交给了 ForkJoinPool 来完成。
 *
 * http://blog.dyngr.com/blog/2016/09/15/java-forkjoinpool-internals/
 */
public class ForkJoinCalculator implements Calculator {

    private ForkJoinPool pool;

    private static class SumTask extends RecursiveTask<Long> {
        private long[] numbers;
        private int from;
        private int to;

        public SumTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            // 当需要计算的数字小于6时，直接计算结果
            if (to - from < 6) {
                long total = 0;
                for (int i = from; i <= to; i++) {
                    total += numbers[i];
                }
                return total;
                // 否则，把任务一分为二，递归计算
            } else {
                int middle = (from + to) / 2;
                SumTask taskLeft = new SumTask(numbers, from, middle);
                SumTask taskRight = new SumTask(numbers, middle + 1, to);
                taskLeft.fork();
                taskRight.fork();
                return taskLeft.join() + taskRight.join();
            }
        }
    }

    public ForkJoinCalculator() {
        // 也可以使用公用的 ForkJoinPool：
        // pool = ForkJoinPool.commonPool()
        pool = new ForkJoinPool();
    }

    @Override
    public long sumUp(long[] numbers) {
        return pool.invoke(new SumTask(numbers, 0, numbers.length - 1));
    }

    public static void main(String[] args) {
        long[] numbers = LongStream.rangeClosed(1, 1000).toArray();
        Calculator calculator = new ForkJoinCalculator();
        long result = calculator.sumUp(numbers);
        System.out.println(result);
    }
}
