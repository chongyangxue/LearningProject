package com.learning.common;

public class BinaryCode {
    static void procedure() {
        try {
            int c[] = {1};
            c[10] = 99;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("数组越界异常" + e);
        }
    }

    public static void main(String args[]) {
        System.out.printf("%x", 1);
        System.out.println();
        System.out.printf("%x", -1); //负数采用补码表示，补码=原码取反+1
        System.out.println();
        System.out.printf("%x", -2);
        System.out.println();
        System.out.printf("%x", -1 << 29);
    }


}
