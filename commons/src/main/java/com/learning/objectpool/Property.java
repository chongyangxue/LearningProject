package com.learning.objectpool;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author xuechongyang
 */
@Data
@NoArgsConstructor
public class Property {

    private String key;
    private Object value;

    public Property(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public static void main(String[] args) {
        Map<Property, Object> map = Maps.newHashMap();
        Property property = new Property("1", 2);
        map.put(property, "1");
        System.out.println(map.get(property));

    }
}
