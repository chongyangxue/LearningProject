package com.learning.Serializable;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.*;

/**
 * Created by xuechongyang on 16/12/20.
 */
public class JsonSerialize {
    private static final String FILE_PATH = "/opt/serialize/jsonResult_1L";

    @Test
    public void write() throws IOException {
        Person p = new Person(1, "Jack", 20);
        String result = JSONObject.toJSONString(p);
        FileUtils.writeStringToFile(new File(FILE_PATH), result);
    }

    @Test
    public void read() throws IOException {
        String json = FileUtils.readFileToString(new File(FILE_PATH));
        Person p = JSONObject.parseObject(json, Person.class);
        System.out.println(p.toString());
    }
}
