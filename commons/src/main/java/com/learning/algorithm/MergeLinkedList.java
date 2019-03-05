/** 
 * File: MergeLinkedList.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 将N个有序链表合并为一个有序链表 注意：归并排序的时间复杂度是O(N*M) 使用堆排序的时间复杂度是O(NlogN)
 */
public class MergeLinkedList {

    class ListNode {
        int val;

        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    private void exchangeElements(ArrayList<ListNode> heap, int index1, int index2) {
        ListNode temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

    private void heapify(ArrayList<ListNode> heap, int index) {
        int position = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < heap.size() && heap.get(left).val < heap.get(position).val) {
            position = left;
        }

        if (right < heap.size() && heap.get(right).val < heap.get(position).val) {
            position = right;
        }

        if (position != index) {
            exchangeElements(heap, position, index);
            heapify(heap, position);
        }
    }

    private void minHeapify(ArrayList<ListNode> heap) {
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            heapify(heap, i);
        }
    }

    public ListNode mergeList(List<ListNode> lists) {
        ArrayList<ListNode> heap = new ArrayList<ListNode>(lists.size());
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i) != null) {
                heap.add(lists.get(i));
            }
        }
        minHeapify(heap); //初始化堆
        ListNode head = null;
        ListNode current = null;
        if (heap.size() == 0)
            return null;
        while (heap.size() != 0) {
            if (head == null) {
                head = heap.get(0);
                current = head;
            } else {
                current.next = heap.get(0);
                current = current.next;
            }

            if (current.next != null) {
                heap.set(0, current.next);
                heapify(heap, 0);
            } else {
                heap.remove(0);
                minHeapify(heap);
            }
        }
        return head;
    }

    @Test
    public void test() {
        ListNode n1 = new ListNode(1);
        ListNode n7 = new ListNode(2);
        ListNode n9 = new ListNode(3);
        ListNode n2 = new ListNode(4);
        ListNode n4 = new ListNode(5);
        ListNode n8 = new ListNode(6);

        ArrayList<ListNode> lists = new ArrayList<ListNode>();
        n1.next = n7;
        n7.next = n9;
        n2.next = n4;
        n4.next = n8;

        lists.add(n1);
        lists.add(n2);

        ListNode head = mergeList(lists);
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }
}
