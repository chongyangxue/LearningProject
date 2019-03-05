/** 
 * File: TopN.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.algorithm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.common.collect.Lists;

import com.learning.common.User;

/**
 * Description: Author: Sachiel Date: 2016年6月3日
 */
public class TopN {

    /**
     * 
     * 根据属性排序得到最大的n个对象
     * 
     * @author：Sachiel
     * @param list
     *            数据列表
     * @param fieldName
     *            根据这个属性名称对列表中的对象进行排序
     * @param n
     *            获取最大的对象的数量
     * @return
     */
    public static <T> List<T> getTopN(List<T> list, String fieldName, int n) {
        List<T> heap = new ArrayList<T>(n);
        try {
            Field field = list.get(0).getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            for (int i = 0; i < list.size(); i++) {
                T obj = list.get(i);
                if (i < n) {
                    heap.add(obj);
                    if (i == (n - 1)) {
                        buildHeap(heap, field);
                    }
                } else {
                    if (compare(field, obj, heap.get(0)) > 0) {
                        heap.set(0, obj);
                    }
                    minHeapify(heap, field, 0, n);
                }
//                for (T t : heap) {
//                    System.out.print(field.get(t) + " ");
//                }
//                System.out.println();
            }

            for (int i = heap.size() - 1; i >= 1; i--) {
                exchangeElements(heap, 0, i);
                minHeapify(heap, field, 0, i);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return heap;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <T> int compare(Field field, T t1, T t2) throws Exception {
        Comparable compare1 = (Comparable) field.get(t1);
        Comparable compare2 = (Comparable) field.get(t2);
        return compare1.compareTo(compare2);

    }

    private static <T> void exchangeElements(List<T> list, int index1, int index2) {
        T temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

    /**
     * 小根堆的调整过程，从heap[position], heap[left], heap[right]中找出最小的
     * 
     * @param <T>
     * 
     * @author：Sachiel
     * @param heap
     * @param index
     */
    private static <T> void minHeapify(List<T> heap, Field field, int index, int size) {
        int position = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        try {
            T leftObj = left < size ? heap.get(left) : null;
            T rightObj = right < size ? heap.get(right) : null;
            T posObj = heap.get(position);

            if (leftObj != null && compare(field, leftObj, posObj) < 0) {
                position = left;
                posObj = heap.get(position);
            }
            if (rightObj != null && compare(field, rightObj, posObj) < 0) {
                position = right;
            }
            if (position != index) {
                exchangeElements(heap, position, index);
                minHeapify(heap, field, position, size);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自底向上调整小根推
     * 
     * @author：Sachiel
     * @param heap
     * @param fieldName
     * @return
     */
    public static <T> List<T> buildHeap(List<T> heap, Field field) {
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            minHeapify(heap, field, i, heap.size());
        }
        return heap;
    }

    public static void main(String[] args) {
        List<User> list = Lists.newArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        list.add(new User(9, "xue", "18", calendar.getTime()));
        calendar.add(Calendar.MINUTE, 1);
        list.add(new User(7, "xue", "18", calendar.getTime()));
        calendar.add(Calendar.MINUTE, 1);
        list.add(new User(8, "xue", "18", calendar.getTime()));
        calendar.add(Calendar.MINUTE, 1);
        list.add(new User(6, "xue", "18", calendar.getTime()));
        calendar.add(Calendar.MINUTE, 1);
        list.add(new User(4, "xue", "18", calendar.getTime()));
        calendar.add(Calendar.MINUTE, 1);
        list.add(new User(12, "xue", "18", calendar.getTime()));
        calendar.add(Calendar.MINUTE, 1);
        list.add(new User(13, "xue", "18", calendar.getTime()));
        calendar.add(Calendar.MINUTE, 1);
        list.add(new User(14, "xue", "18", calendar.getTime()));
        calendar.add(Calendar.MINUTE, 1);
        list.add(new User(3, "xue", "18", calendar.getTime()));
        calendar.add(Calendar.MINUTE, 1);
        list.add(new User(2, "xue", "18", calendar.getTime()));
        calendar.add(Calendar.MINUTE, 1);
        list.add(new User(1, "xue", "18", calendar.getTime()));

        List<User> result = getTopN(list, "startTime", 7);
        for (User user : result) {
            System.out.print(user.getUserid() + " ");
        }
    }
}
