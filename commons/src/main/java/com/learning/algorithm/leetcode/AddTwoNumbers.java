package com.learning.algorithm.leetcode;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * Input: (2 -> 4 -> 1) + (5 -> 6 -> 4) Output: 7 -> 0 -> 6
 */
public class AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Long number1 = (long) l1.val;
        Long number2 = (long) l2.val;
        for (long i = 10; l1.next != null; i *= 10) {
            ListNode next = l1.next;
            number1 += next.val * i;
            l1 = next;
        }
        for (long i = 10; l2.next != null; i *= 10) {
            ListNode next = l2.next;
            number2 += next.val * i;
            l2 = next;
        }
        Long result = number1 + number2;
        ListNode node = new ListNode(((Long) (result % 10)).intValue());
        result = result / 10;
        ListNode first = node;
        while (result != 0) {
            ListNode newNode = new ListNode(((Long) (result % 10)).intValue());
            node.next = newNode;
            node = newNode;
            result = result / 10;
        }
        return first;
    }

    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }


    public static void main(String[] args) {
        AddTwoNumbers clazz = new AddTwoNumbers();
        ListNode l1 = new ListNode(9);
        ListNode l2 = new ListNode(1);
        ListNode tmp = l2;
        for (int i = 0; i < 9; i++) {
            tmp.next = new ListNode(9);
            tmp = tmp.next;
        }
        ListNode result = clazz.addTwoNumbers(l1, l2);
        while (result != null) {
            System.out.print(result.val + ",");
            result = result.next;
        }
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

}


