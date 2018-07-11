package com.sachiel.spi.impl.c;

import com.sachiel.spi.api.DemoApi;

/**
 * @author xuechongyang
 */
public class DemoApiC implements DemoApi {

    @Override
    public String sayHello(String name) {
        return "DemoApiC say: Hello " + name;
    }
}
