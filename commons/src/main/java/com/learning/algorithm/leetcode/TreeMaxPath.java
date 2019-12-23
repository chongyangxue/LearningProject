package com.learning.algorithm.leetcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个二叉树，找到这颗树最大的路径
 *
 * @author xuechongyang
 */
public class TreeMaxPath {


    /**
     * 获取二叉树最大路径的和
     */
    private int maxTreePathSum(TreeNode node) {
        int sumLeft = 0;
        int sumRight = 0;
        if (node.getLeft() == null && node.getRight() == null) {
            return node.getValue();
        }
        if (node.getLeft() != null) {
            sumLeft = node.getValue() + maxTreePathSum(node.getLeft());
        }
        if (node.getRight() != null) {
            sumRight = node.getValue() + maxTreePathSum(node.getRight());
        }
        return sumLeft > sumRight ? sumLeft : sumRight;
    }


    /**
     * 获取二叉树最大路径
     * 回溯算法
     */
    int max = 0;
    List<Integer> result = new ArrayList<>();
    List<Integer> tmp = new ArrayList<>();
    private List<Integer> maxTreePath(TreeNode node, int sum) {
        sum += node.getValue();
        tmp.add(node.getValue());
        if (node.getLeft() == null && node.getRight() == null) {
            if (sum > max) {
                max = sum;
                result.clear();
                result.addAll(new ArrayList<>(tmp));
            }
        }
        if (node.getLeft() != null) {
            maxTreePath(node.getLeft(), sum);
        }
        if (node.getRight() != null) {
            maxTreePath(node.getRight(), sum);
        }
        tmp.remove(tmp.size() - 1);
        return result;
    }

    List<List<Integer>> list = new ArrayList<>();
    List<Integer> inner = new ArrayList<>();

    /**
     * 返回和为指定数的二叉树路径
     *
     * @param node 二叉树头结点
     * @param sum  指定数
     * @return 匹配的路径
     */
    public List<List<Integer>> pathSum(TreeNode node, int sum) {
        if (node == null) {
            return list;
        }
        sum -= node.getValue();
        // 入列表
        inner.add(node.getValue());
        if (node.left == null && node.right == null) {
            if (sum == 0) {
                // 记得拷贝一份
                list.add(new ArrayList<>(inner));
            }
        }
        if (node.left != null) {
            pathSum(node.left, sum);
        }
        if (node.right != null) {
            pathSum(node.right, sum);
        }
        //从列表中删除
        inner.remove(inner.size() - 1);
        return list;
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
        System.out.println(maxTreePathSum(n1));
        System.out.println(pathSum(n1, maxTreePathSum(n1)));
        System.out.println(maxTreePath(n1, 0));
    }

    @Data
    @AllArgsConstructor
    class TreeNode {
        private TreeNode left;
        private TreeNode right;
        private int value;

        public TreeNode(int value) {
            this.value = value;
        }
    }
}
