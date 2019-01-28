package com.learning.off_heap.bytebuffer;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xuechongyang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Property implements Serializable{

    private String key;
    private Object value;


    public static void main(String[] args) {
        Map<Property, Object> map = Maps.newHashMap();
        Property property = new Property("1", 2);
        map.put(property, "1");
        System.out.println(map.get(property));

    }
}
