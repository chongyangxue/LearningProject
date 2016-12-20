package com.learning.Serializable;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: YangXuehua
 * Date: 13-4-26
 * Time: 下午2:25
 * hession序列化、反序列化工具类
 */
public class HessianSerialize {

    private static final String FILE_PATH = "/opt/serialize/hessianSerializeResult_1L";

    @Test
    public void write() throws Exception {
        Person p = new Person(1, "Jack", 20);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HessianOutput out = new HessianOutput(bos);
        out.writeObject(p);
        out.flush();
        byte[] bytes = bos.toByteArray();
        FileUtils.writeByteArrayToFile(new File(FILE_PATH), bytes);
    }

    @Test
    public void read() throws Exception {
        byte[] bytes = FileUtils.readFileToByteArray(new File(FILE_PATH));
        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        HessianInput in = new HessianInput(bin);
        Object o = in.readObject(Object.class);
        System.out.println(o.toString());
    }
}
