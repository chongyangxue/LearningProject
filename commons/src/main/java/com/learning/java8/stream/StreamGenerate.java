package com.learning.java8.stream;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by xuechongyang on 17/6/4.
 */
public class StreamGenerate {

    @Test
    public void generate() {
        Stream.iterate(1, num -> num + 1).limit(10).forEach(num -> System.out.println(num));
        List<Object> result = Stream.generate(() -> null).limit(10).collect(Collectors.toList());

        System.out.println(result);
    }
}
