package com.learning.algorithm;

public class MMchess {
    static int MMchess(int[] Nscores, int[] Mnumbers) {
        // Nscores : the scores on the the board (the i-th integer corresponds to the score on the i-th grid)
        // Mnumbers : the numbers on the M cards
        // return value : the most score that player can get
        int max = 0;

        for (int i = 0; i < Mnumbers.length; i++) {
            for (int j = i + 1; j < Mnumbers.length; j++) {
                int[] tempArray = Mnumbers;
                int score = Nscores[0];
                for (int k = 0, index = 0; k < tempArray.length; k++) {
                    index += tempArray[k];
                    score += Nscores[index];
                }
                if (score > max)
                    max = score;
                for (int k = 0; k < tempArray.length; k++) {
                    System.out.print(tempArray[k] + " ");
                }
                System.out.println("===>" + max);

                if (Mnumbers[i] != Mnumbers[j]) {
                    int score1 = Nscores[0];
                    tempArray = swap(tempArray, i, j);
                    for (int k = 0, index = 0; k < tempArray.length; k++) {
                        index += tempArray[k];
                        score1 += Nscores[index];
                    }
                    if (score1 > max)
                        max = score1;
                }

                for (int k = 0; k < tempArray.length; k++) {
                    System.out.print(tempArray[k] + " ");
                }
                System.out.println("===>" + max);
            }
        }
        return max;
    }

    public static int[] swap(int[] array, int from, int to) {
        int tmp = array[from];
        array[from] = array[to];
        array[to] = tmp;
        return array;
    }

    public static void main(String[] args) {
        int[] N = new int[]{1, 2, 4, 2, 6, 1};
        int[] M = new int[]{1, 2, 2};
        System.out.println(MMchess(N, M));
    }

}
