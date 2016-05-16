/** 
 * File: HeapSort.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package Algorithm;

/**
 * Description: Author: Sachiel Date: 2016年5月12日
 */
public class HeapSort {

    public static void exchangeElements(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    /**
     * 小根堆的调整过程，从heap[position], heap[left], heap[right]中找出最小的
     * 
     * @author：Sachiel
     * @param heap
     * @param index
     */
    private void minHeapify(int[] heap, int index, int size) {
        int position = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < size && heap[left] < heap[position]) {
            position = left;
        }
        if (right < size && heap[right] < heap[position]) {
            position = right;
        }
        if (position != index) {
            exchangeElements(heap, position, index);
            minHeapify(heap, position, size);
        }
    }
    
    /**
     * 大根堆的调整过程，从heap[position], heap[left], heap[right]中找出最大的
     * 
     * @author：Sachiel
     * @param heap
     * @param index
     */
    private void maxHeapify(int[] heap, int index, int size) {
        int position = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < size && heap[left] > heap[position]) {
            position = left;
        }

        if (right < size && heap[right] > heap[position]) {
            position = right;
        }

        if (position != index) {
            exchangeElements(heap, position, index);
            maxHeapify(heap, position, size);
        }
    }

    public int[] heapSort(int[] heap) {
        //初始化堆
        for (int i = heap.length / 2 - 1; i >= 0; i--) {
            maxHeapify(heap, i, heap.length);
        }

        for (int i = heap.length - 1; i >= 1; i--) {
            exchangeElements(heap, 0, i);
            maxHeapify(heap, 0, i);
        }
        return heap;
    }

    public static void main(String[] args) {
        HeapSort sort = new HeapSort();
        int[] array = new int[] {1, 3, 7, 4, 9, 2, 6, 8, 5, 0};
        sort.heapSort(array);
        for (int i : array) {
            System.out.print(i + " ");
        }
    }
}
