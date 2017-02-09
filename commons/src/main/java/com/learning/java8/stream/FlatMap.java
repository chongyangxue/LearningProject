package com.learning.java8.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by xuechongyang on 17/1/6.
 */
public class FlatMap {

    @Test
    public void testFlatMap() {

        String[][] data = new String[][]{{"a", "b"}, {"c", "d"}, {"e", "f"}};

        Stream<String[]> temp = Arrays.stream(data);
        Stream<String> stringStream = temp.flatMap(x -> Arrays.stream(x));
        stringStream.forEach(System.out::println);

		/*Stream<String> java8 = Arrays.java8(data)
                .flatMap(x -> Arrays.java8(x))
                .filter(x -> "a".equals(x.toString()));*/
    }
}
