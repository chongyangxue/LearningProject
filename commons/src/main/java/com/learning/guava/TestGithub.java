package com.learning.guava;

public class TestGithub {

    public void printMatrixClockwise(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // 定义四个边界
        int left = 0, right = cols - 1;
        int top = 0, bottom = rows - 1;
        
        while (left <= right && top <= bottom) {
            // 从左到右
            for (int i = left; i <= right; i++) {
                System.out.print(matrix[top][i] + " ");
            }
            top++;
            
            // 从上到下
            for (int i = top; i <= bottom; i++) {
                System.out.print(matrix[i][right] + " ");
            }
            right--;
            
            // 从右到左
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    System.out.print(matrix[bottom][i] + " ");
                }
                bottom--;
            }
            
            // 从下到上
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    System.out.print(matrix[i][left] + " ");
                }
                left++;
            }
        }
    }

    public static void main(String[] args) {
        TestGithub test = new TestGithub();
        int[][] matrix = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        };
        test.printMatrixClockwise(matrix);
    }
    
}
