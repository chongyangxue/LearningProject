package Algorithm.Leetcode;

/**
 * You are given two linked lists representing two non-negative numbers. The
 * digits are stored in reverse order and each of their nodes contain a single
 * digit. Add the two numbers and return it as a linked list.
 * 
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4) Output: 7 -> 0 -> 8
 * 
 * 
 */
public class AddTwoNumbers {
	
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		Long number1 = (long)l1.val;
		Long number2 = (long)l2.val;
		for(long i = 10; l1.next != null; i *= 10){
			ListNode next = l1.next;
			number1 += next.val * i;
			l1 = next;
		}
		for(long i = 10; l2.next != null; i *= 10){
			ListNode next = l2.next;
			number2 += next.val * i;
			l2 = next;
		}
		Long result = number1 + number2;
		ListNode node = new ListNode(((Long)(result % 10)).intValue());
		result = result / 10;
		ListNode first = node;
		while(result != 0){
			ListNode newNode = new ListNode(((Long)(result % 10)).intValue());
			node.next = newNode;
			node = newNode;
			result = result / 10;
		}
		return first;
	}
	
	public static void main(String[] args){
		AddTwoNumbers clazz = new AddTwoNumbers();
		ListNode l1 = new ListNode(9);
		ListNode l2 = new ListNode(1);
		ListNode tmp = l2;
		for(int i=0; i < 9; i++){
			tmp.next = new ListNode(9);
			tmp = tmp.next;
		}
		ListNode result = clazz.addTwoNumbers(l1, l2);
		while(result != null){
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


