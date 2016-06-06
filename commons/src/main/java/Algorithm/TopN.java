/** 
 * File: TopN.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package Algorithm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.common.collect.Lists;

import common.User;

/**
 * Description: Author: Sachiel Date: 2016年6月3日
 */
public class TopN {

    /**
     * 
     * 根据属性排序得到最大的n个对象
     * @author：Sachiel 
     * @param list          数据列表
     * @param fieldName     根据这个属性名称对列表中的对象进行排序
     * @param n             获取最大的对象的数量
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T> List<T> getTopN(List<T> list, String fieldName, int n) {
        List<T> heap = new ArrayList<T>(n);
        try {
            Field field = list.get(0).getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            for (int i = 0; i < list.size(); i++) {
                T obj = list.get(i);
                if (i < n) {
                    heap.add(obj);
                } else {
                    minHeapSort(heap, fieldName);
                    Comparable newValue = (Comparable) field.get(obj);
                    Comparable topValue = (Comparable) field.get(heap.get(0));
                    if (newValue.compareTo(topValue) > 0) {
                        heap.set(0, obj);
                    }
                }
            }

            minHeapify(heap, fieldName, 0, n);
            for (int i = heap.size() - 1; i >= 1; i--) {
                exchangeElements(heap, 0, i);
                minHeapify(heap, fieldName, 0, i);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return heap;
    }

    private <T> void exchangeElements(List<T> list, int index1, int index2) {
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
    @SuppressWarnings({"rawtypes", "unchecked"})
    private <T> void minHeapify(List<T> heap, String fieldName, int index, int size) {
        int position = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        try {
            Field field = heap.get(0).getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Comparable posValue = (Comparable) field.get(heap.get(position));
            Comparable leftValue = left < size ? (Comparable) field.get(heap.get(left)) : null;
            Comparable rightValue = right < size ? (Comparable) field.get(heap.get(right)) : null;

            if (leftValue != null && leftValue.compareTo(posValue) < 0) {
                position = left;
                posValue = (Comparable) field.get(heap.get(position));
            }
            if (rightValue != null && rightValue.compareTo(posValue) < 0) {
                position = right;
            }
            if (position != index) {
                exchangeElements(heap, position, index);
                minHeapify(heap, fieldName, position, size);
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
    public <T> List<T> minHeapSort(List<T> heap, String fieldName) {
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            minHeapify(heap, fieldName, i, heap.size());
        }
        return heap;
    }

    public static void main(String[] args) {
        TopN top = new TopN();
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

        List<User> result = top.getTopN(list, "startTime", 7);
        for (User user : result) {
            System.out.print(user.getUserid() + " ");
        }
    }
}
