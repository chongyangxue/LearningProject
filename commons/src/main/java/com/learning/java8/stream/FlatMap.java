package com.learning.java8.stream;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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

		List<List<Integer>> listList = Lists.newArrayList();
        listList.add(Lists.newArrayList(1, 2, 3));
        listList.add(Lists.newArrayList(4, 5, 6));
        listList.add(Lists.newArrayList(7, 8, 9));
        List<Integer> result = listList.stream().flatMap(list -> list.stream()).collect(Collectors.toList());
        System.out.println(result);

    }
}
