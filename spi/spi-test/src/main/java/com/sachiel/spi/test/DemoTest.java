package com.sachiel.spi.test;

import com.sachiel.spi.api.DemoApi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author xuechongyang
 */

public class DemoTest {


    public static void main(String[] args){
        ServiceLoader<DemoApi> serviceLoader = ServiceLoader.load(DemoApi.class);
        Iterator<DemoApi> iterator = serviceLoader.iterator();
        System.out.println("遍历多种可能存在的实现:");
        while (iterator.hasNext()) {
            DemoApi demoApi = iterator.next();
            System.out.println(demoApi.sayHello("meituan"));
        }
    }
}
