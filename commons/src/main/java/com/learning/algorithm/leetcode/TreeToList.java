package com.learning.algorithm.leetcode;

import lombok.Data;
import org.junit.Test;

/**
 * 给定一个二叉树，原地将它展开为链表。
 * 例如，给定二叉树
 * <p>
 * 1
 * / \
 * 2   5
 * / \   \
 * 3   4   6
 * 将其展开为：
 * 1
 * \
 * 2
 * \
 * 3
 * \
 * 4
 * \
 * 5
 * \
 * 6
 */
public class TreeToList {

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        flatten(root.left);
        flatten(root.right);

        TreeNode leftTail = root.left;
        while (leftTail != null && leftTail.right != null) {
            leftTail = leftTail.right;
        }

        if (leftTail != null) {
            leftTail.right = root.right;
        }
        if (root.left != null) {
            root.right = root.left;
        }
        root.left = null;
    }


    @Test
    public void test() {
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(1);
        TreeNode n5 = new TreeNode(2);
        TreeNode n6 = new TreeNode(5);

        n1.setLeft(n2);
        n1.setRight(n3);
        n2.setLeft(n4);
        n2.setRight(n5);
        n4.setLeft(n6);
        flatten(n1);
        System.out.println(n1);
    }

    @Data
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
