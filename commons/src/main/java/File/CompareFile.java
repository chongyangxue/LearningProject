/** 
 * File: CompareFile.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package File;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Description: Author: Sachiel Date: 2016-3-4
 */
public class CompareFile {

    private static Map<String, JSONObject> getContentMap(String name) throws IOException {
        List<String> list = FileUtils.readLines(new File(name), "UTF-8");
        System.out.println(name + " 有  " + list.size() + "条");
        Map<String, JSONObject> map = Maps.newHashMap();
        for (String str : list) {
            JSONObject obj = JSON.parseObject(str);
            String key = obj.getString("ip");
            JSONObject content = map.get(key);
            if (content != null) {
                System.out.println(name + " 重复内容: " + content + ", " + str);
            } else {
                map.put(key, obj);
            }
        }
        return map;
    }

    private static int getDiffSize(Map<String, JSONObject> map1, Map<String, JSONObject> map2) {
        List<JSONObject> diff = Lists.newArrayList();
        for (String ip : map1.keySet()) {
            if (map2.get(ip) == null) {
                diff.add(map1.get(ip));
            }
        }
        return diff.size();
    }

    public static void main(String[] args) throws IOException {
        String name1 = "/opt/147.txt";
        String name2 = "/opt/148.txt";
        Map<String, JSONObject> map1 = getContentMap(name1);
        Map<String, JSONObject> map2 = getContentMap(name2);
        System.out.println(map1.size());
        System.out.println(String.format("%s 比  %s 多余：%d条", name1, name2, getDiffSize(map1, map2)));
        System.out.println(String.format("%s 比  %s 多余：%d条", name2, name1, getDiffSize(map2, map1)));
    }
}
