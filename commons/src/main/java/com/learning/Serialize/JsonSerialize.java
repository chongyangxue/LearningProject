package com.learning.Serialize;

import com.alibaba.fastjson.JSONObject;
import com.learning.Serialize.test.Person;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.*;

/**
 * Created by xuechongyang on 16/12/20.
 */
public class JsonSerialize {
    private static final String FILE_PATH = "/opt/serialize/json";

    public static void write(Object o) throws IOException {
        String result = JSONObject.toJSONString(o);
        FileUtils.writeStringToFile(new File(FILE_PATH), result);
    }

    @Test
    public void read() throws IOException {
        String json = FileUtils.readFileToString(new File(FILE_PATH));
        Person p = JSONObject.parseObject(json, Person.class);
        System.out.println(p.toString());
    }
}
