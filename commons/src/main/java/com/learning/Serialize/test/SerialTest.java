package com.learning.Serialize.test;

import com.learning.Serialize.*;

import java.io.Serializable;

/**
 * Created by xuechongyang on 16/12/23.
 */
public class SerialTest extends Person implements Serializable {

    public SerialTest() {
        super("1", "Jack");
    }

    public static void main(String args[]) throws Exception {
        SerialTest obj = new SerialTest();
        long startTime = System.currentTimeMillis();
        JavaSerialize.write(obj);
        long javaTime = System.currentTimeMillis();
        System.out.println("java time: " +  (javaTime - startTime));

//        SpyMemcachedSerialize.write(obj);
//        long spyMemcacheTime = System.currentTimeMillis();
//        System.out.println("spyMemcacheTime time: " +  (spyMemcacheTime - javaTime));

        HessianSerialize.write(obj);
        long hessianTime = System.currentTimeMillis();
        System.out.println("hessianTime time: " +  (hessianTime - javaTime));

        KyroSerialize.write(obj);
        long kyroTime = System.currentTimeMillis();
        System.out.println("kyroTime time: " +  (kyroTime - hessianTime));

        JsonSerialize.write(obj);
        long jsonTime = System.currentTimeMillis();
        System.out.println("json time: " +  (jsonTime - kyroTime));
    }
}
