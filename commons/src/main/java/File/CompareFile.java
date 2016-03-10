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
    public static void main(String[] args) throws IOException {
        File file1 = new File("/opt/34.txt");
        File file2 = new File("/opt/36.txt");
        List<String> sort34 = FileUtils.readLines(file1, "UTF-8");
        List<String> sort36 = FileUtils.readLines(file2, "UTF-8");
        System.out.println("34:" + sort34.size() + ", 36:" + sort36.size());

        Map<String, JSONObject> map1 = Maps.newHashMap();
        Map<String, JSONObject> map2 = Maps.newHashMap();
        for (String str : sort34) {
            JSONObject obj = JSON.parseObject(str);
            String key = obj.getString("ip");
            JSONObject content = map1.get(key);
            if (content != null) {
                System.out.println("sort34 duplicated: " + content + ", " + str);
            } else {
                map1.put(key, obj);
            }
        }

        for (String str : sort36) {
            JSONObject obj = JSON.parseObject(str);
            String key = obj.getString("ip");
            JSONObject content = map2.get(key);
            if (content != null) {
                System.out.println("sort36 duplicated: " + content + ", " + str);
            } else {
                map2.put(key, obj);
            }
        }

        List<JSONObject> diff1 = Lists.newArrayList();
        for (String ip : map2.keySet()) {
            JSONObject obj = map2.get(ip);
            if (map1.get(ip) == null) {
                diff1.add(obj);
            }
        }
        //FileUtils.writeLines(new File("/opt/diff3.txt"), diff1);
        System.out.println("36上多余的：" + diff1.size() );

        List<JSONObject> diff2 = Lists.newArrayList();
        for (String ip : map1.keySet()) {
            JSONObject obj = map1.get(ip);
            if (map2.get(ip) == null) {
                diff2.add(obj);
            }
        }
        System.out.println("34上多余的：" + diff2.size());

    }
}
