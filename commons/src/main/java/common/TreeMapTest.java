/** 
 * File: TreeMapTest.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package common;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * Description: Author: Sachiel Date: 2016年5月10日
 */
public class TreeMapTest {

    public class Url {
        String url;

        Integer count;
        
        public Url(String name, Integer count) {
            this.url = name;
            this.count = count;
        }

        public Integer getCount() {
            return count;
        }
    }

    public static void main(String[] args) {
        TreeMap<Url, Integer> treeMap = new TreeMap<Url, Integer>(new Comparator<Url>() {

            @Override
            public int compare(Url u1, Url u2) {
                return u1.getCount() - u2.getCount();
            }

        });
        TreeMapTest test = new TreeMapTest();
        treeMap.put(test.new Url("xue", 10), 100);
    }
}
