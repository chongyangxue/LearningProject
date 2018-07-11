package com.sachiel.spi.impl.b;

import com.sachiel.spi.api.DemoApi;

/**
 * @author xuechongyang
 */
public class DemoApiB implements DemoApi {

    @Override
    public String sayHello(String name) {
        return "DemoApiB say: Hello " + name;
    }
}
